package dk.ek.stud.group8.delfinen.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public final class SystemTerminal implements Terminal {
    private final Scanner in;
    private final PrintStream out;

    public SystemTerminal(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    @Override
    public void print(String str) {
        out.print(str);
        out.flush();
    }

    @Override
    public void println(String str) {
        System.out.println(str);
    }

    @Override
    public String readLine() {
        return in.nextLine();
    }
}
