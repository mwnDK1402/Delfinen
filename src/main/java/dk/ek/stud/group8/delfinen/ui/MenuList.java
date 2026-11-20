package dk.ek.stud.group8.delfinen.ui;

import static dk.ek.stud.group8.delfinen.ui.ANSI.*;

public final class MenuList {
    private final Terminal terminal;
    private final String title;
    private final String[] options;

    public MenuList(Terminal terminal, String title, String... options) {
        this.terminal = terminal;
        this.title = title;
        this.options = options;
    }

    public int choose() {
        terminal.println(BOLD + title + RESET);
        for (int i = 0; i < options.length; i++) {
            terminal.println((i + 1) + ". " + options[i]);
        }

        // Get number in range from user
        final var min = 1;
        final var max = options.length;

        int choice;
        while (true) {
            final String errorPrefix = "Ugyldigt valg: ";

            String input = terminal.readLine("INT (" + min + ".." + max + ")");
            try {
                choice = Integer.parseInt(input);
                if (choice < min || choice > max) {
                    throw new IndexOutOfBoundsException(choice);
                }

                break;
            } catch (NumberFormatException e) {
                terminal.println(errorPrefix
                        + "Brug tal, ikke bogstaver. "
                        + "Tallet skal være mellem " + min + " og " + max + ".");
            } catch (IndexOutOfBoundsException e) {
                terminal.println(errorPrefix
                        + "Skriv et heltal mellem " + min + " og " + max + ".");
            }
        }

        return choice - 1;
    }

    public static final class Factory {
        private final Terminal terminal;

        public Factory(Terminal terminal) {
            this.terminal = terminal;
        }

        public MenuList create(String title, String... options) {
            return new MenuList(terminal, title, options);
        }
    }
}
