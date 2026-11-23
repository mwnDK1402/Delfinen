package dk.ek.stud.group8.delfinen.ui;

public final class MenuList {
    private final Terminal terminal;
    private final Messages message;
    private final String title;
    private final String[] options;

    public MenuList(Terminal terminal, Messages message, String title, String... options) {
        this.terminal = terminal;
        this.message = message;
        this.title = title;
        this.options = options;
    }

    public int choose() {
        terminal.println(title);
        for (int i = 0; i < options.length; i++) {
            terminal.println((i + 1) + ". " + options[i]);
        }

        // Get number in range from user
        final var min = 1;
        final var max = options.length;

        // readIntInRange(min, max)

        int choice;
        while (true) {
            terminal.println();
            terminal.println();
            terminal.print(ANSI.GRAY);
            terminal.println(message.prompt(min, max));
            terminal.print(ANSI.RESET);
            terminal.print(ANSI.CPL(2));
            terminal.print("> ");
            String input = terminal.readLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    break;
                }

                // Out of range
                terminal.println(message.outOfRange(min, max, choice));
            } catch (NumberFormatException e) {
                // Not a number
                terminal.println(message.notANumber(min, max, input));
            }
        }

        return choice - 1;
    }

    public interface Messages {
        String prompt(int min, int max);

        String notANumber(int min, int max, String actual);

        String outOfRange(int min, int max, int actual);
    }

    public static final class Factory {
        private final Terminal terminal;
        private final Messages messages;

        public Factory(Terminal terminal, Messages messages) {
            this.terminal = terminal;
            this.messages = messages;
        }

        public MenuList create(String title, String... options) {
            return new MenuList(terminal, messages, title, options);
        }
    }
}
