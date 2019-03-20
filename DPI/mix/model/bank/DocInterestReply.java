package model.bank;

import java.io.Serializable;

/**
 * This class stores information about the Hospital reply
 *  to a loan request of the specific client
 * 
 */
public class DocInterestReply implements Serializable{

    private double interest; // the loan interest
    private String bankId; // the nunique quote Id
    
    public DocInterestReply() {
        this.interest = 0;
        this.bankId = "";
    }
    
    public DocInterestReply(double interest, String quoteId) {
        this.interest = interest;
        this.bankId = quoteId;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getQuoteId() {
        return bankId;
    }

    public void setQuoteId(String quoteId) {
        this.bankId = quoteId;
    }

    public String toString() {
        return "quote=" + this.bankId + " interest=" + this.interest;
    }
}
