package dk.ek.stud.group8.delfinen.persistence;

public class PaymentFileHandler {
    private String fileName;
    private static final String SEPARATOR = "\t";
    private static final String HEADER = String.join(SEPARATOR, "memberId", "name", "startDate", "endDate", "amount", "paid");
    private int skippedLineCount;

    public PaymentFileHandler(String fileName) {
        this.fileName = fileName;
    }

    // Load method
    // Save method
    // Get warnings
    // Parse method
    // toString method
}
