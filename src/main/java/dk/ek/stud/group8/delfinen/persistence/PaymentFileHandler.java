package dk.ek.stud.group8.delfinen.persistence;

import java.nio.file.Path;

public class PaymentFileHandler {
    private final Path fileName;
    private static final String SEPARATOR = "\t";
    private static final String HEADER = String.join(SEPARATOR, "memberId", "name", "startDate", "endDate", "amount", "paid");
    private int skippedLineCount;

    public PaymentFileHandler(Path fileName) {
        this.fileName = fileName;
    }

    // Load method
    // Save method
    // Get warnings
    // Parse method
    // toString method
}
