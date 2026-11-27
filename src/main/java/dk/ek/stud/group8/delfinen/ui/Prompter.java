package dk.ek.stud.group8.delfinen.ui;

import java.util.function.Function;

public final class Prompter {
    private final Terminal terminal;

    public Prompter(Terminal terminal) {
        this.terminal = terminal;
    }

    public <T> T ask(String prompt, Function<String, Result<T>> parser) {
        while (true) {
            printPrompt(prompt);
            String input = terminal.readLine();
            var result = parser.apply(input);
            switch (result) {
                case Result.Ok<T> o -> {
                    return o.value();
                }
                case Result.Err<T> e -> terminal.println(e.message());
            }
        }
    }

    private void printPrompt(String prompt) {
        terminal.println();
        terminal.println();
        terminal.print(ANSI.GRAY);
        terminal.println(prompt);
        terminal.print(ANSI.RESET);
        terminal.print(ANSI.CPL(2));
        terminal.print("> ");
    }
}
