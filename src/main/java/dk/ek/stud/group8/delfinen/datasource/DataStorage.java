package dk.ek.stud.group8.delfinen.datasource;

import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MembershipFee;

import java.util.ArrayList;

public class DataStorage {

    private final MemberFileHandler memberHandler;
    private final PaymentFileHandler paymentHandler;
    private final ResultsFileHandler resultHandler;
    private String warnings = "";

    public DataStorage(String dataDirectory) {
        this.memberHandler = new MemberFileHandler(dataDirectory + "/medlemmer.tsv");
        this.paymentHandler = new PaymentFileHandler(dataDirectory + "/betalinger.tsv");
        this.resultHandler = new ResultsFileHandler(dataDirectory + "/resultater.tsv");
    }

    public MemberRegister loadAll() {
        warnings = "";

        MemberRegister register = new MemberRegister(memberHandler.load());

        ArrayList<MembershipFee> payments = paymentHandler.load();
        for (MembershipFee payment : payments) {
            Member member = register.findMember(payment.getMemberId());
            if (member == null) {
                warnings += "Betaling sprunget over: medlem " + payment.getMemberId() + " findes ikke.\n";
                continue;
            }
            try {
                member.addPayment(payment);
            } catch (IllegalArgumentException e) {
                warnings += "Betaling for medlem " + payment.getMemberId() + " sprunget over: " + e.getMessage() + "\n";
            }
        }

        ArrayList<Result> results = resultHandler.load();
        for (Result result : results) {
            Member member = register.findMember(result.getMemberId());
            if (member == null) {
                warnings += "Resultat sprunget over: medlem " + result.getMemberId() + " findes ikke\n";
                continue;
            }
            try {
                member.addResult(result);
            } catch (IllegalArgumentException e) {
                warnings += "Resultatet for medlem " + result.getMemberId() + " sprunget over: " + e.getMessage() + "\n";
            }
        }

        warnings = memberHandler.getWarnings()
                + paymentHandler.getWarnings()
                + resultHandler.getWarnings()
                + warnings;

        return register;
    }

    public void saveAll(MemberRegister register) {
        memberHandler.save(register.getMembers());
        paymentHandler.save(register.getAllPayments());
        resultHandler.save(register.getAllResults());
        warnings = memberHandler.getWarnings()
                + paymentHandler.getWarnings()
                + resultHandler.getWarnings();
    }

    public String getWarnings() {
        return warnings;
    }
}
