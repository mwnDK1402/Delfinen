package dk.ek.stud.group8.delfinen.ui;

public final class MenuList {
    private final Terminal terminal;
    private final String[] options;

    public MenuList(Terminal terminal, String... options) {
        this.terminal = terminal;
        this.options = options;
    }

    public void choose() {
        for (int i = 0; i < options.length; i++) {
            terminal.println((i + 1) + ". " + options[i]);
        }
    }

    public static final class Factory {
        private final Terminal terminal;

        public Factory(Terminal terminal) {
            this.terminal = terminal;
        }

        public MenuList create(String... options) {
            return new MenuList(terminal, options);
        }
    }
}
