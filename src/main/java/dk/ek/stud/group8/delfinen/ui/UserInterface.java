package dk.ek.stud.group8.delfinen.ui;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.ActivityType;
import dk.ek.stud.group8.delfinen.domain.Discipline;
import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MembershipType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserInterface {
    private final Terminal terminal;
    private final Controller controller;

    public UserInterface(Terminal terminal, Controller controller) {
        this.terminal = Objects.requireNonNull(terminal);
        this.controller = Objects.requireNonNull(controller);
    }

    public void start() throws InterruptedException {
        boolean running = true;
        try {
            controller.loadAll();
        } catch (IOException e) {
            terminal.println("Din gemte data kunne ikke indlæses: " + e.getMessage());
            if (askQuit()) return;
        }

        if (controller.hasStorageWarnings()) {
            String warnings = controller.getStorageWarnings();
            terminal.println("Advarsel: " + warnings);
            if (askQuit()) return;
        }

        while (running) {
            terminal.println("==== Svømmeklubben Delfinen ====");
            terminal.println("1. Formand");
            terminal.println("2. Kasserer");
            terminal.println("3. Træner");
            terminal.println("0. Afslut\n");

            int choice = readInt("> Valg: ");

            switch (choice) {
                case 1 -> chairpersonMenu();
                case 2 -> terminal.println("Tilføj cashierMenu()");   // cashierMenu();
                case 3 -> terminal.println("Tilføj tilføj coachMenu()");   // coachMenu();
                case 0 -> running = false;
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    private boolean askQuit() throws InterruptedException {
        terminal.println("Er du sikker på, du vil fortsætte?");
        var cont = readBoolean("> Fortsæt: ");
        if (!cont) {
            terminal.println("Programmet lukkes");
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    private void chairpersonMenu() {
        boolean running = true;
        while (running) {
            terminal.println("--- Formand ---");
            terminal.println("1. Opret medlem");
            terminal.println("2. Vis alle medlemmer");
            terminal.println("0. Tilbage\n");

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> createMember();
                case 2 -> showAllMembers();
                case 0 -> running = false;
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    public void createMember() {
        String name = readNonEmptyString("Navn: ");
        LocalDate birthDate = readBirthDate("Fødselsdato (YYYY-MM-dd): ");
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
            controller.saveAll();
            terminal.println(name + " er oprettet.");
        } catch (IllegalArgumentException e) {
            terminal.println("Medlemmet kunne ikke oprettes: " + e.getMessage());
        } catch (IOException e) {
            terminal.println("Medlemmet blev oprettet, men filen kunne ikke blive gemt: " + e.getMessage());
        }
    }

    private void showAllMembers() {
        terminal.println("Alle medlemmer:");

        List<Member> members = controller.getMembers();
        if (members.isEmpty()) {
            terminal.println("Listen er tom.");
        }
        for (Member m : members) {
            terminal.println("Navn: " + m.getName());
        }
    }

private String readNonEmptyString(String prompt) {
    while (true) {
        terminal.print(prompt);
        String input = terminal.readLine().trim();
        if (input.isEmpty()) {
            terminal.println("Feltet må ikke være tomt.");
            continue;
        }
        if (input.contains("\t")) {
            terminal.println("Du tror du er smart, hva'?");
            continue;
        }
        return input;
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
            terminal.println(prompt);
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

    private boolean readBoolean(String prompt) {
        while (true) {
            terminal.print(prompt);
            String input = terminal.readLine().trim().toLowerCase(Locale.ROOT);
            switch (input) {
                case "j", "ja", "y" -> {
                    return true;
                }
                case "n", "nej" -> {
                    return false;
                }
                default -> terminal.println("Skriv ja/nej (eller j/n).");
            }
        }
    }

    private ArrayList<Discipline> askForDisciplines() {
        ArrayList<Discipline> chosen = new ArrayList<>();
        Discipline[] all = Discipline.values();

        while (true) {
            terminal.println("Vælg disciplin (mindt én):");
            for (int i = 0; i < all.length; i++) {
                terminal.println((i + 1) + ") " + all[i]);
            }
            terminal.println("0) Færdig");
            terminal.print("Valg: ");

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
            terminal.println("Medlemskab:");
            terminal.println("1. Aktivt");
            terminal.println("2. Passivt");

            int choice = readInt("Valg: ");
            switch (choice) {
                case 1 -> {
                    return MembershipActivityStatus.ACTIVE;
                }
                case 2 -> {
                    return MembershipActivityStatus.PASSIVE;
                }
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    private ActivityType askForActivityType() {
        while (true) {
            terminal.println("Aktivitetsform:");
            terminal.println("1. Motionist");
            terminal.println("2. Konkurrencesvømmer");

            int choice = readInt("Valg: ");
            switch (choice) {
                case 1 -> {
                    return new ActivityType.Recreational();
                }
                case 2 -> {
                    return new ActivityType.Competitive(new HashSet<>());
                }
                default -> terminal.println("Ugyldigt valg.");
            }
        }
    }

    enum MembershipActivityStatus {
        ACTIVE,
        PASSIVE
    }
}
