package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MemberRegistry;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public final class DataStorage {
    private final MemberRegistry memberRegistry;
    private final MemberFileHandler memberHandler;
    private final PaymentFileHandler paymentHandler;
    private final ResultsFileHandler resultHandler;
    private final Path dataDirectory;
    private String warnings = "";

    public DataStorage(MemberRegistry memberRegistry, Path dataDirectory) {
        this.memberRegistry = memberRegistry;
        this.memberHandler = new MemberFileHandler(dataDirectory.resolve("medlemmer.tsv"));
        this.paymentHandler = new PaymentFileHandler(dataDirectory.resolve("betalinger.tsv"));
        this.resultHandler = new ResultsFileHandler(dataDirectory.resolve("resultater.tsv"));
        this.dataDirectory = dataDirectory;
    }

    public void loadAll(LocalDate now) throws IOException {
        warnings = "";
        Files.createDirectories(dataDirectory);
        List<Member> members = memberHandler.load(now);
        memberRegistry.load(members);

        // TODO(#5): Load MembershipFees
        /*
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
        */

        // TODO(#8): Load training results
        /*
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
        */

        warnings = memberHandler.getWarnings() + warnings;
                /*
                + paymentHandler.getWarnings()
                + resultHandler.getWarnings()
                */
    }

    public void saveAll() throws IOException {
        List<Member> members = memberRegistry.getMembers();
        memberHandler.save(members);
        // TODO(#5): Save MembershipFees
        //paymentHandler.save(registry.getAllPayments());
        // TODO(#8): Save training results
        //resultHandler.save(registry.getAllResults());
        warnings = String.join(System.lineSeparator(),
                memberHandler.getWarnings()
                /*
                paymentHandler.getWarnings(),
                resultHandler.getWarnings()
                */
        );
    }

    public boolean hasWarnings() {
        return memberHandler.getSkippedLineCount() > 0;
    }

    public String getWarnings() {
        return warnings;
    }
}
