package dk.ek.stud.group8.delfinen.presentation;

import dk.ek.stud.group8.delfinen.ui.ANSI;
import dk.ek.stud.group8.delfinen.ui.MenuList;
import dk.ek.stud.group8.delfinen.ui.Terminal;

public final class Loop {
    private final Terminal terminal;
    private final MenuList.Factory menuFactory;

    public Loop(Terminal terminal, MenuList.Factory menuFactory) {
        this.terminal = terminal;
        this.menuFactory = menuFactory;
    }

    public void start() {
        terminal.print(ANSI.ENABLE_ALT_SCREEN_BUF);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            terminal.print(ANSI.DISABLE_ALT_SCREEN_BUF);
        }));

        terminal.println("Velkommen til Delfinen!");

        // TODO: Role menu
        // [ Vælg rolle ]
        // 1. Formand
        // 2. Kasserer
        // 3. Træner

        String[] options = {
                "Formand",
                "Kasserer",
                "Træner"
        };
        var roleMenu = menuFactory.create(Style.title("Vælg en rolle"), options);

        var choiceIdx = roleMenu.choose();
        var choice = options[choiceIdx];

        terminal.println("Du valgte " + choice + "!");
    }
}
