package dk.ek.stud.group8.delfinen.persistence;

public class ResultsFileHandler {
    private String fileName;
    private static final String SEPARATOR = "\t";
    private static final String HEADER = String.join(SEPARATOR, "memberId", "name", "discipline", "type", "recordTime", "date");
    private int skippedLineCount;

    public ResultsFileHandler(String fileName) {
        this.fileName = fileName;
    }

    // Load method
    // Save method
    // Get warnings
    // Parse method
    // toString method
}
