package Hospital;

import controllers.receiverGateway;
import controllers.senderGateway;
import interfaces.IsenderGateway;
import messaging.requestreply.HealthCheckReply;
import model.bank.DocInterestReply;
import model.bank.DocInterestRequest;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Doc2 extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private IsenderGateway sendergateway;
    private JPanel contentPane;
    private JTextField tfReply;
    private DefaultListModel<HealthCheckReply<DocInterestRequest, DocInterestReply>> listModel = new DefaultListModel<HealthCheckReply<DocInterestRequest, DocInterestReply>>();
    private List<HealthCheckReply<DocInterestRequest, String>> waitingForReply;
    private receiverGateway receivergateway;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
                    Doc2 frame = new Doc2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Doc2() {
        sendergateway = new senderGateway();
        receivergateway = new receiverGateway("toING");
        receivergateway.addObserver(this::update);
        setTitle("JMS Bank - Doc2");

        waitingForReply = new ArrayList<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
        gbl_contentPane.rowHeights = new int[]{233, 23, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 5;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        contentPane.add(scrollPane, gbc_scrollPane);

        JList<HealthCheckReply<DocInterestRequest, DocInterestReply>> list = new JList<HealthCheckReply<DocInterestRequest, DocInterestReply>>(listModel);
        scrollPane.setViewportView(list);

        JLabel lblNewLabel = new JLabel("type reply");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        tfReply = new JTextField();
        GridBagConstraints gbc_tfReply = new GridBagConstraints();
        gbc_tfReply.gridwidth = 2;
        gbc_tfReply.insets = new Insets(0, 0, 0, 5);
        gbc_tfReply.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfReply.gridx = 1;
        gbc_tfReply.gridy = 1;
        contentPane.add(tfReply, gbc_tfReply);
        tfReply.setColumns(10);

        JButton btnSendReply = new JButton("send reply");
        btnSendReply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HealthCheckReply<DocInterestRequest, DocInterestReply> rr = list.getSelectedValue();
                double interest = Double.parseDouble((tfReply.getText()));
                DocInterestReply reply = new DocInterestReply(interest,"Doc2");
                if (rr!= null && reply != null){
                    rr.setReply(reply);
                    list.repaint();

                    for (int i = 0; i < waitingForReply.size(); i++) {
                        System.out.println("Checking:" + i);
                        System.out.println(waitingForReply.get(i).getRequest().getAmount() + " | " + rr.getRequest().getAmount());
                        if(waitingForReply.get(i).getRequest() == rr.getRequest()){
                            sendergateway.messageSomeOne(rr.getReply(), waitingForReply.get(i).getReply(), "ReplyToBroker");
                            break;
                        }
                    }
                }
            }
        });
        GridBagConstraints gbc_btnSendReply = new GridBagConstraints();
        gbc_btnSendReply.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnSendReply.gridx = 4;
        gbc_btnSendReply.gridy = 1;
        contentPane.add(btnSendReply, gbc_btnSendReply);

    }

    @Override
    public void update(Observable o, Object msg) {
        System.out.println("yoyo tijd");
        DocInterestRequest bankinterestrequest;
        try {
            bankinterestrequest = (DocInterestRequest) ((ObjectMessage) msg).getObject();
            String correlation = ((ObjectMessage) msg).getJMSCorrelationID();
            waitingForReply.add(new HealthCheckReply<>(bankinterestrequest, correlation));
            listModel.addElement(new HealthCheckReply<>(bankinterestrequest, new DocInterestReply()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}