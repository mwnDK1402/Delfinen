package dk.ek.stud.group8.delfinen.ui;

public final class MenuList {
    private final Terminal terminal;
    private final Messages messages;
    private final Prompter prompter;
    private final String title;
    private final String[] options;

    public MenuList(Terminal terminal, Messages messages, Prompter prompter, String title, String... options) {
        this.terminal = terminal;
        this.messages = messages;
        this.prompter = prompter;
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

        int choice = prompter.ask(messages.prompt(min, max), input -> {
            try {
                var parsed = Integer.parseInt(input);
                if (parsed >= min && parsed <= max) {
                    return new Result.Ok<>(parsed);
                }

                return new Result.Err<>(messages.outOfRange(min, max, parsed));
            } catch (NumberFormatException e) {
                return new Result.Err<>(messages.notANumber(min, max, input));
            }
        });

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
        private final Prompter prompter;

        public Factory(Terminal terminal, Messages messages, Prompter prompter) {
            this.terminal = terminal;
            this.messages = messages;
            this.prompter = prompter;
        }

        public MenuList create(String title, String... options) {
            return new MenuList(terminal, messages, prompter, title, options);
        }
    }
}
