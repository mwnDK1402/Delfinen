package dk.ek.stud.group8.delfinen.ui;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.ActivityType;
import dk.ek.stud.group8.delfinen.domain.Discipline;
import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MembershipType;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final Controller controller;

    public UserInterface(InputStream in, Controller controller) {
        scanner = new Scanner(Objects.requireNonNull(in));
        this.controller = Objects.requireNonNull(controller);
    }

    public void start() {
        boolean running = true;
        // TODO: loadMembers();

        while (running) {
            System.out.println("==== Svømmeklubben Delfinen ====");
            System.out.println("1. Formand");
            System.out.println("2. Kasserer");
            System.out.println("3. Træner");
            System.out.println("0. Afslut\n");

            int choice = readInt("> Valg: ");

            switch (choice) {
                case 1 -> chairpersonMenu();
                case 2 -> System.out.println("Tilføj cashierMenu()");   // cashierMenu();
                case 3 -> System.out.println("Tilføj tilføj coachMenu()");   // coachMenu();
                case 0 -> running = false;
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }

    private void chairpersonMenu() {
        boolean running = true;
        while (running) {
            System.out.println("--- Formand ---");
            System.out.println("1. Opret medlem");
            System.out.println("2. Vis alle medlemmer");
            System.out.println("0. Tilbage\n");

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> createMember();
                case 2 -> showAllMembers();
                case 0 -> running = false;
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }

    public void createMember() {
        String name = readNonEmptyString("Navn: ");
        LocalDate birthDate = readBirthDate("Fødselsdato (YYYY-MM-dd): ");
        MembershipType membershipType = askForMembershipType(); // Spørger efter aktiv eller passiv
        ActivityType activityType = null;
        ArrayList<Discipline> disciplines = new ArrayList<>();

        if (membershipType == membershipType.ACTIVE) {
            activityType = askForActivityType(); // Motionist eller konkurrencesvømmer
            if (activityType == activityType.Competitive) {
                disciplines = askForDisciplines();
            } else {
                disciplines = new ArrayList<>();
            }
        }

        try {
            controller.addMember(name, birthDate, membershipType, activityType, disciplines, LocalDate.now());
            System.out.println(name + " er oprettet.");
        } catch (IllegalArgumentException e) {
            System.out.println("Medlemmet kunne ikke oprettes: " + e.getMessage());
        }
    }

    private void showAllMembers() {
        System.out.println("Alle medlemmer:");

        ArrayList<Member> members = controller.getMembers();
        if (members.isEmpty()) {
            System.out.println("Listen er tom.");
        }
        for (Member m : members) {
            System.out.println("Navn: " + m.getName());
        }
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Feltet må ikke være tomt.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Skriv et tal.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig data. Brug formattet yyyy-MM-dd, f.eks. 2010-06-12.");
            }
        }
    }

    private LocalDate readBirthDate(String prompt) {
        while (true) {
            LocalDate date = readDate(prompt);
            if (date.isAfter(LocalDate.now())) {
                System.out.println("Fødselsdatoen kan ikke ligge i fremtiden.");
            } else {
                return date;
            }
        }
    }

    private ArrayList<Discipline> askForDisciplines() {
        ArrayList<Discipline> chosen = new ArrayList<>();
        Discipline[] all = Discipline.values();

        while (true) {
            System.out.println("Vælg disciplin (mindt én):");
            for (int i = 0; i < all.length; i++) {
                System.out.println((i + 1) + ") " + all[i]);
            }
            System.out.println("0) Færdig");
            System.out.print("Valg: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Skriv et tal.");
                continue;
            }

            if (choice == 0) {
                if (chosen.isEmpty()) {
                    System.out.println("Du skal vælge mindst én disciplin.");
                    continue;
                }
                return chosen;
            }

            if (choice < 1 || choice > all.length) {
                System.out.println("Ugyldigt valg.");
                continue;
            }

            Discipline d = all[choice - 1];
            if (chosen.contains(d)) {
                System.out.println(d + " er allerede valgt.");
            } else {
                chosen.add(d);
                System.out.println(d + " tilføjet.");
            }
        }
    }

    private MembershipType askForMembershipType() {
        while (true) {
            System.out.println("Medlemskab:");
            System.out.println("1. Aktivt");
            System.out.println("2. Passivt");

            int choice = readInt("Valg: ");
            switch (choice) {
                case 1 -> {
                    return MembershipType.ACTIVE;
                }
                case 2 -> {
                    return MembershipType.PASSIVE;
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }

    private ActivityType askForActivityType() {
        while (true) {
            System.out.println("Aktivitetsform:");
            System.out.println("1. Motionist");
            System.out.println("2. Konkurrencesvømmer");

            int choice = readInt("Valg: ");
            switch (choice) {
                case 1 -> {
                    return ActivityType.Recreational;
                }
                case 2 -> {
                    return ActivityType.Competitive;
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }
}
