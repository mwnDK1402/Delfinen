package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.presentation.Loop;
import dk.ek.stud.group8.delfinen.ui.SystemTerminal;
import dk.ek.stud.group8.delfinen.ui.Terminal;

public class Main {
    static void main() {
        Terminal terminal = new SystemTerminal(System.in, System.out);
        new Loop(terminal).start();
    }
}
