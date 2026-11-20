package dk.ek.stud.group8.delfinen.presentation;

import dk.ek.stud.group8.delfinen.ui.MenuList;
import dk.ek.stud.group8.delfinen.ui.Terminal;

public final class Loop {
    private final Terminal terminal;

    public Loop(Terminal terminal) {
        this.terminal = terminal;
    }

    public void start() {
        terminal.println("Velkommen til Delfinen!");

        // TODO: Role menu
        // [ Vælg rolle ]
        // 1. Formand
        // 2. Kasserer
        // 3. Træner

        var menuFactory = new MenuList.Factory(terminal);

        String[] options = {
                "Formand",
                "Kasserer",
                "Træner"};
        var roleMenu = menuFactory.create(options);

        var choiceIdx = roleMenu.choose();
        var choice = options[choiceIdx];

        terminal.println("Du valgte " + choice + "!");
    }
}
