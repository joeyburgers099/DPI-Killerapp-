package model.broker;

import model.bank.DocInterestReply;

import java.util.Comparator;

public class DocInterestComp implements Comparator<DocInterestReply> {
    @Override
    public int compare(DocInterestReply a, DocInterestReply b) {
        if (a.getInterest() < b.getInterest())
            return 1; // lowest value first
        if (a.getInterest() == b.getInterest())
            return 0;

        return -1;
    }
}