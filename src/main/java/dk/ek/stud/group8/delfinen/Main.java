package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.MemberRegistry;
import dk.ek.stud.group8.delfinen.ui.*;

import java.util.Random;

public class Main {
    static void main() {
        /*Terminal terminal = new SystemTerminal(System.in, System.out);
        var messages = new StyledMessages(new DanishMessages());
        MenuList.Factory menuFactory = new MenuList.Factory(terminal, messages, new Prompter(terminal));
        new Loop(terminal, menuFactory).start();*/

        MemberRegistry memberRegistry = new MemberRegistry(new Random());
        Controller controller = new Controller(memberRegistry);
        UserInterface ui = new UserInterface(System.in, controller);
        ui.start();
    }
}