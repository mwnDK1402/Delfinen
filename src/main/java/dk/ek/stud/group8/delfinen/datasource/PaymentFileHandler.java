package dk.ek.stud.group8.delfinen.datasource;

import dk.ek.stud.group8.delfinen.domain.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentFileHandler {

    private String fileName;
    private static final String SEPARATOR = "\t";
    private static final String HEADER = String.join(SEPARATOR, "memberId", "name", "year", "amount", "paidDate");
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
