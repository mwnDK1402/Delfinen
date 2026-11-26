package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.MemberRegistry;
import dk.ek.stud.group8.delfinen.ui.*;

import java.util.Random;

public class Main {
    static void main() {
        MemberRegistry memberRegistry = new MemberRegistry(new Random());
        Controller controller = new Controller(memberRegistry);
        Terminal terminal = new SystemTerminal(System.in, System.out);
        UserInterface ui = new UserInterface(terminal, controller);
        ui.start();
    }
}