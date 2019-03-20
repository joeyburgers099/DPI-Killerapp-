package interfaces;

import messaging.requestreply.HealthCheckReply;
import model.bank.DocInterestReply;
import model.bank.DocInterestRequest;
import model.loan.HealthCheckRequest;

public interface IsenderGateway {
    void messageSomeOne(HealthCheckReply reply, String correlation, String Queue);
    void messageSomeOne(HealthCheckRequest request, String Queue);
    void messageSomeOne(DocInterestRequest request, String correlation, String Queue);
    void messageSomeOne(DocInterestReply request, String correlation, String Queue);
}