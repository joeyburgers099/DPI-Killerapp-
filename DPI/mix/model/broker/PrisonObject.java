package model.broker;

import model.bank.DocInterestReply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrisonObject {
    private int numberOut;
    private List<DocInterestReply> replyList;
    private String Correlation;

    public PrisonObject(int nummer, String cor){
        replyList = new ArrayList<>();
        numberOut = nummer;
        Correlation = cor;

    }

    public boolean add(DocInterestReply reply){
        replyList.add(reply);
        System.out.println("Expected returns: " + numberOut);
        System.out.println("Current Returns: " + replyList.size());
        if(replyList.size() == numberOut){
            System.out.println("Returning true, sending to client after");
            return true;
        }
        else{
            System.out.println("Returning False, try again");
            return false;
        }
    }
    public String getCorrelation() {
        return Correlation;
    }
    public DocInterestReply getReply(){
        DocInterestReply finalReply = Collections.max(replyList, new DocInterestComp());
        return finalReply;
    }
}