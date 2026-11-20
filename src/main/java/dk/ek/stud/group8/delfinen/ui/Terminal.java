package dk.ek.stud.group8.delfinen.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public interface Terminal {
    void print(String str);
    default void println(String str) {
        print(str + System.lineSeparator());
    }
    String readLine(String prompt);
    default String readLine() {
        return readLine("");
    }
}

