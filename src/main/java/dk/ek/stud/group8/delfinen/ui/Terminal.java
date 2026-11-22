package dk.ek.stud.group8.delfinen.ui;

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

