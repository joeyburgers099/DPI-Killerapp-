package Prison;

import model.loan.*;
import model.bank.*;

/**
 * This class represents one line in the JList in Loan Broker.
 * This class stores all objects that belong to one HealthCheckRequest:
 *    - HealthCheckRequest,
 *    - DocInterestRequest, and
 *    - DocInterestReply.
 *  Use objects of this class to add them to the JList.
 *
 * @author 884294
 *
 */
class JListLine {

    private HealthCheckRequest healthCheckRequest;
    private DocInterestRequest bankRequest;
    private DocInterestReply bankReply;

    public JListLine(HealthCheckRequest healthCheckRequest) {
        this.setHealthCheckRequest(healthCheckRequest);
    }

    public HealthCheckRequest getHealthCheckRequest() {
        return healthCheckRequest;
    }

    public void setHealthCheckRequest(HealthCheckRequest healthCheckRequest) {
        this.healthCheckRequest = healthCheckRequest;
    }

    public DocInterestRequest getBankRequest() {
        return bankRequest;
    }

    public void setBankRequest(DocInterestRequest bankRequest) {
        this.bankRequest = bankRequest;
    }

    public DocInterestReply getBankReply() {
        return bankReply;
    }

    public void setBankReply(DocInterestReply bankReply) {
        this.bankReply = bankReply;
    }

    @Override
    public String toString() {
        return healthCheckRequest.toString() + " || " + ((bankReply != null) ? bankReply.toString() : "waiting for reply...");
    }

}