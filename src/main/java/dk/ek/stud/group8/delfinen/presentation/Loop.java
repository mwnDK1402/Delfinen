package dk.ek.stud.group8.delfinen.presentation;

import dk.ek.stud.group8.delfinen.ui.Terminal;

public final class Loop {
    private final Terminal terminal;

    public Loop(Terminal terminal) {
        this.terminal = terminal;
    }

    public void start() {
        terminal.println("Velkommen til Delfinen!");
    }
}
