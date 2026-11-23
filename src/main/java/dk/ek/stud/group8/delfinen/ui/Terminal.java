package dk.ek.stud.group8.delfinen.ui;

public interface Terminal {
    void print(String str);
    void println(String str);
    default void println() {
        println("");
    }

    String readLine();
}

