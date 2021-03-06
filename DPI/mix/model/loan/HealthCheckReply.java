package model.loan;

import java.io.Serializable;

/**
 *
 * This class stores all information about a Hospital offer
 * as a response to a client loan request.
 */
public class HealthCheckReply implements Serializable {

        private double interest; // the interest that the Hospital offers
        private String bankID; // the unique quote identification

    public HealthCheckReply() {
        super();
        this.interest = 0;
        this.bankID = "";
    }
    public HealthCheckReply(double interest, String quoteID) {
        super();
        this.interest = interest;
        this.bankID = quoteID;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getQuoteID() {
        return bankID;
    }

    public void setQuoteID(String quoteID) {
        this.bankID = quoteID;
    }
    
    @Override
    public String toString(){
        return " interest="+String.valueOf(interest) + " quoteID="+String.valueOf(bankID);
    }
}
