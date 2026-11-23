package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.i18n.DanishMessages;
import dk.ek.stud.group8.delfinen.presentation.Loop;
import dk.ek.stud.group8.delfinen.presentation.StyledMessages;
import dk.ek.stud.group8.delfinen.ui.MenuList;
import dk.ek.stud.group8.delfinen.ui.SystemTerminal;
import dk.ek.stud.group8.delfinen.ui.Terminal;

public class Main {
    static void main() {
        Terminal terminal = new SystemTerminal(System.in, System.out);
        var messages = new StyledMessages(new DanishMessages());
        MenuList.Factory menuFactory = new MenuList.Factory(terminal, messages);
        new Loop(terminal, menuFactory).start();
    }
}
