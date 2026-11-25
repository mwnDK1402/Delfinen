package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.MemberRegister;
import dk.ek.stud.group8.delfinen.i18n.DanishMessages;
import dk.ek.stud.group8.delfinen.presentation.Loop;
import dk.ek.stud.group8.delfinen.presentation.StyledMessages;
import dk.ek.stud.group8.delfinen.ui.*;

public class Main {
    static void main() {
        /*Terminal terminal = new SystemTerminal(System.in, System.out);
        var messages = new StyledMessages(new DanishMessages());
        MenuList.Factory menuFactory = new MenuList.Factory(terminal, messages, new Prompter(terminal));
        new Loop(terminal, menuFactory).start();*/

        MemberRegister memberRegister = new MemberRegister();
        Controller controller = new Controller(memberRegister);
        UserInterface ui = new UserInterface(System.in, controller);
        ui.start();
    }
}