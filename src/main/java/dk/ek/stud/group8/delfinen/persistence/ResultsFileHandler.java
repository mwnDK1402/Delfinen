package dk.ek.stud.group8.delfinen.persistence;

import java.nio.file.Path;

public class ResultsFileHandler {
    private final Path fileName;
    private static final String SEPARATOR = "\t";
    private static final String HEADER = String.join(SEPARATOR, "memberId", "name", "discipline", "type", "recordTime", "date");
    private int skippedLineCount;

    public ResultsFileHandler(Path fileName) {
        this.fileName = fileName;
    }

    // Load method
    // Save method
    // Get warnings
    // Parse method
    // toString method
}
