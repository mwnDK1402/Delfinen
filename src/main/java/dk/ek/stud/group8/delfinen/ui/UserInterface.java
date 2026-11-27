package dk.ek.stud.group8.delfinen.ui;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.*;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserInterface {
    private final Terminal terminal;
    private final Controller controller;
    private Style Style;

    public UserInterface(Terminal terminal, Controller controller) {
        this.terminal = Objects.requireNonNull(terminal);
        this.controller = Objects.requireNonNull(controller);
    }

    public void start() {
        boolean running = true;
        // TODO: loadMembers();

        while (running) {
            showWelcome();
            terminal.println();
            terminal.println("Vælg rolle:");
            terminal.println();
            terminal.println(Style.menuItem("1", "Formand", "Opret og se medlemmer"));
            terminal.println(Style.menuItem("2", "Kasserer", "Kontingent og restance"));
            terminal.println(Style.menuItem("3", "Træner", "Hold, tider og top 5"));
            terminal.println();
            terminal.println(Style.menuItem("0", "Afslut", ""));
            terminal.println();

            int choice = readInt("> Vælg: ");

            switch (choice) {
                case 1 -> chairpersonMenu();
                case 2 -> cashierMenu();
                case 3 -> coachMenu();
                case 0 -> running = false;
                default -> terminal.println(Style.error("Ugyldigt valg."));
            }
        }
        terminal.println();
        terminal.println(Style.success("Farvel, og tak for i dag!"));
    }

    private void chairpersonMenu() {
        boolean running = true;
        while (running) {
            //terminal.println(Style.heading("Formand", "Vælg handling"));
            printHeader("Formand", "Vælg handling", controller.getMembers().size() + " medlemmer");
            terminal.println();
            terminal.println(Style.menuItem("1", "Opret medlem", ""));
            terminal.println(Style.menuItem("2", "Vis alle medlemmer", ""));
            terminal.println();
            terminal.println(Style.menuItem("0", "← Tilbage", ""));
            terminal.println();

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> createMember();
                case 2 -> showAllMembers();
                case 0 -> running = false;
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    private void cashierMenu() {
        boolean running = true;
        while (running) {
            printHeader("Kasserer", "Vælg handling", "Kontingent " + "2026");
            terminal.println();
            terminal.println(Style.statLabels(24, "Forventet indtægt", "Indbetalt", "Udestående"));
            terminal.println(Style.statValues(24,
                    Style.money(12000),
                    Style.money(8000),
                    Style.money(4000)));
            terminal.println();
            terminal.println(Style.progressBar(66, 32, "betalt"));
            terminal.println();

            terminal.println(Style.menuItem("1", "Se kontingent", ""));
            terminal.println(Style.menuItem("2", "Registrer betaling", ""));
            terminal.println(Style.menuItem("3", "Se medlemmer i restance", ""));
            terminal.println();
            terminal.println(Style.menuItem("0", "← Tilbage", ""));
            terminal.println();

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> terminal.println("Metode mangler");
                case 2 -> terminal.println("Metode mangler");
                case 0 -> running = false;
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    private void coachMenu() {
        boolean running = true;
        while (running) {
            printHeader("Træner", "Vælg handling", "Efterår " + "2026");
            terminal.println();
            terminal.println(Style.menuItem("1", "Tilføj metoder", ""));
            terminal.println(Style.menuItem("2", "Tilføj metoder", ""));
            terminal.println(Style.menuItem("0", "← Tilbage", ""));
            terminal.println();

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> terminal.println("Metode mangler");
                case 2 -> terminal.println("Metode mangler");
                case 0 -> running = false;
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    public void showWelcome() {
        terminal.println();
        terminal.println(Style.boxTop());
        terminal.println(Style.boxLine(""));
        terminal.println(Style.boxLine("SVØMMEKLUBBEN DELFINEN"));
        terminal.println(Style.boxLine("Medlemsadministration · v1.0"));
        terminal.println(Style.boxLine(""));
        terminal.println(Style.boxBottom());
        terminal.println(Style.statusLine(LocalDate.now(),
                "Medlemmer: " + controller.getMembers().size()));
        terminal.println(Style.divider());
    }

    private void printHeader(String title, String subTitle, String rightText) {
        terminal.println();
        for (String line : Style.headerBox(title, subTitle, rightText)) {
            terminal.println(line);
        }
    }

    // TODO - Fix blanding af terminal metoder og sout
    public void createMember() {
        terminal.println();
        terminal.println(Style.statusLineTwo(Style.bold("FORMAND" + Style.getBreadcrumbChevron() + "OPRET MEDLEM"), "(skriv 0 for at annullere)"));
        terminal.println(Style.divider());
        // terminal.println(Style.dim("(skriv 0 for at annullere)"));
        terminal.println();
        terminal.println(Style.helperTextForm("Brug fornavn og efternavn, som det skal stå på medlemskortet"));
        String name = readNonEmptyString("Navn: ");
        terminal.println();
        terminal.println(Style.helperTextForm("Skriv f.eks. 1993-06-12 ((YYYY-MM-dd)). Alder og junior/senior udregnes automatisk"));
        LocalDate birthDate = readBirthDate("Fødselsdato: ");
        terminal.println();
        MembershipActivityStatus membershipType = askForMembershipType(); // Spørger efter aktiv eller passiv
        ActivityType activityType = null; // Motionist eller konkurrencesvømmer

        if (membershipType == MembershipActivityStatus.ACTIVE) {
            activityType = askForActivityType();
            if (activityType instanceof ActivityType.Competitive(Set<Discipline> disciplines)) {
                disciplines.addAll(askForDisciplines());
            }
        }

        try {
            var realMembershipType = switch (membershipType) {
                case ACTIVE -> new MembershipType.Active(activityType);
                case PASSIVE -> new MembershipType.Passive();
            };
            controller.addMember(LocalDate.now(), LocalDate.now(), name, birthDate, realMembershipType);
            terminal.println();
            // TODO - tilføj getMemberId() til success print out
            terminal.println(Style.success("✔ " + name + " er oprettet med medlemsnummer " + "3662."));
        } catch (IllegalArgumentException e) {
            terminal.println(Style.error("Medlemmet kunne ikke oprettes: " + e.getMessage()));
        }
    }

    private void showAllMembers() {
        List<Member> members = controller.getMembers();
        terminal.println(Style.heading("Formand" + Style.getBreadcrumbChevron()+ "Vis alle medlemmer"));

        if (members.isEmpty()) {
            terminal.println(Style.error("Listen er tom."));
        } else {
            LocalDate today = LocalDate.now();

            terminal.println("  "
                    + Style.padding("Nr", 4) + "  "
                    + Style.padding("Navn", 18) + "  "
                    + Style.paddingLeft("Alder", 5) + "  "
                    + Style.padding("Gruppe", 6) + "  "
                    + Style.padding("Aktivitet", 11) + "  "
                    + "Discipliner");
            terminal.println(Style.divider());

            int juniors = 0, seniors = 0, passive = 0, recreational = 0, competitive = 0;

            for (Member m : members) {
                boolean junior = m.getSeniority(today) == Seniority.Junior;
                if (junior) juniors++; else seniors++;

                String activity;
                if (m.isPassive()) {
                    passive++;
                    activity = Style.dim(Style.padding("Passiv", 11));
                } else if (m.isCompetitive()) {
                    competitive++;
                    activity = Style.padding("Konkurrence", 11);
                } else {
                    recreational++;
                    activity = Style.padding("Motionist", 11);
                }

                // TODO - Fix this temporary MemberId fix
                terminal.println("  "
                        // + Style.padding(m.getMemberId().toString(), 4) + "  "   // se note om MemberId
                        + Style.padding("3622", 4) + "  "
                        + Style.padding(m.getName(), 18) + "  "
                        + Style.paddingLeft(String.valueOf(m.getAgeInYears(today)), 5) + "  "
                        + Style.padding(junior ? "Junior" : "Senior", 6) + "  "
                        + activity + "  "
                        + disciplineLabel(m));
            }

            terminal.println(Style.divider());
            terminal.println("  " + Style.dim(members.size() + " medlemmer · "
                    + juniors + " junior · " + seniors + " senior · "
                    + passive + " passiv · " + recreational + " motionist · "
                    + competitive + " konkurrence"));
        }
    }

    private String disciplineLabel(Member m) {
        String text = "";
        for (Discipline d : m.getDisciplines()) {
            text += (text.isEmpty() ? "" : ", ") + d.getDisplayName();   // eller getDisplayName()
        }
        return text;
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            terminal.print(prompt);
            String input = terminal.readLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            terminal.println("Feltet må ikke være tomt.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            terminal.print(prompt);
            try {
                return Integer.parseInt(terminal.readLine().trim());
            } catch (NumberFormatException e) {
                terminal.println("Skriv et tal.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            terminal.print(prompt);
            String input = terminal.readLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                terminal.println("Ugyldig data. Brug formatet yyyy-MM-dd, f.eks. 2010-06-12.");
            }
        }
    }

    private LocalDate readBirthDate(String prompt) {
        while (true) {
            LocalDate date = readDate(prompt);
            if (date.isAfter(LocalDate.now())) {
                terminal.println("Fødselsdatoen kan ikke ligge i fremtiden.");
            } else {
                return date;
            }
        }
    }

    private ArrayList<Discipline> askForDisciplines() {
        ArrayList<Discipline> chosen = new ArrayList<>();
        Discipline[] all = Discipline.values();

        while (true) {
            terminal.println();
            terminal.println(Style.statusLineTwo("Vælg disciplin (mindst én):", "Trin 4 af 4"));
            for (int i = 0; i < all.length; i++) {
                terminal.println("  " + (i + 1) + ". " + all[i].getDisplayName());
            }
            terminal.println("0. Færdig");
            terminal.print("Vælg: ");

            String input = terminal.readLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                terminal.println("Skriv et tal.");
                continue;
            }

            if (choice == 0) {
                if (chosen.isEmpty()) {
                    terminal.println("Du skal vælge mindst én disciplin.");
                    continue;
                }
                return chosen;
            }

            if (choice < 1 || choice > all.length) {
                terminal.println("Ugyldigt valg.");
                continue;
            }

            Discipline d = all[choice - 1];
            if (chosen.contains(d)) {
                terminal.println(d + " er allerede valgt.");
            } else {
                chosen.add(d);
                terminal.println(d + " tilføjet.");
            }
        }
    }

    private MembershipActivityStatus askForMembershipType() {
        while (true) {
            terminal.println(Style.statusLineTwo("Medlemskab:", "Trin 2 af 4"));
            terminal.println("  1. Aktivt");
            terminal.println("  2. Passivt");

            int choice = readInt("Vælg: ");
            switch (choice) {
                case 1 -> {
                    return MembershipActivityStatus.ACTIVE;
                }
                case 2 -> {
                    return MembershipActivityStatus.PASSIVE;
                }
                default -> terminal.println("Ugyldigt valg.\n");
            }
        }
    }

    private ActivityType askForActivityType() {
        while (true) {
            terminal.println();
            terminal.println(Style.statusLineTwo("Aktivitetsform:", "Trin 3 af 4"));
            terminal.println("  1. Motionist");
            terminal.println("  2. Konkurrencesvømmer");

            int choice = readInt("Vælg: ");
            switch (choice) {
                case 1 -> {
                    return new ActivityType.Recreational();
                }
                case 2 -> {
                    return new ActivityType.Competitive(new HashSet<>());
                }
                default -> terminal.println("Ugyldigt valg.\n");
            }
        }
    }

    enum MembershipActivityStatus {
        ACTIVE,
        PASSIVE
    }
}
