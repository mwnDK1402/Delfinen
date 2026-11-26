package dk.ek.stud.group8.delfinen.ui;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.*;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

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
                case 2 -> cashierMenu();
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

    private void cashierMenu() {
        boolean running = true;
        while (running) {
            System.out.println("--- Kasserer ---");
            System.out.println("1. Vis liste af medlemmer i restance");
            System.out.println("2. Registrer kontingent betaling");
            System.out.println("3. Se samlet kontingentindtægt");
            System.out.println("4. Se et medlems årlige kontingent");
            System.out.println("0. Tilbage\n");

            int choice = readInt("> Valg: ");
            switch (choice) {
                case 1 -> System.out.println("Tilføj en metode, der viser medlemmer i restance"); // TODO(#6)
                case 2 -> markAsPaid();
                case 3 -> showEstimatedMembershipFeeTotal();
                case 4 -> System.out.println("Tilføj en metode, der viser et medlems årlige kontingent"); // TODO(#4)
                case 0 -> running = false;
                default -> System.out.println("Ugyldigt valg.");
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
            System.out.println(name + " er oprettet.");
        } catch (IllegalArgumentException e) {
            System.out.println("Medlemmet kunne ikke oprettes: " + e.getMessage());
        }
    }

    private void showAllMembers() {
        System.out.println("Alle medlemmer:");

        List<Member> members = controller.getMembers();
        if (members.isEmpty()) {
            System.out.println("Listen er tom.");
        }
        for (Member m : members) {
            System.out.println("Navn: " + m.getName());
        }
    }

    private void showEstimatedMembershipFeeTotal() {
        int total = controller.getExpectedTotalIncome();

        if (controller.getMembers().isEmpty()) {
            System.out.println("Der er ingen medlemmer.");
        } else {
            System.out.println("Samlet kontingentindtægt: " + total + " kr.");
        }
    }

    private void markAsPaid() {
        int memberId = readInt("Indtast medlems-ID: ");
        MemberId id = new MemberId(memberId);

        try {
            controller.markAsPaid(id);
            System.out.println("Kontingentet er registreret som betalt.");
        } catch (IllegalArgumentException e) {
            System.out.println("Medlemmet blev ikke fundet.");
        } catch (IllegalStateException e) {
            System.out.println("Kontingentet er allerede betalt.");
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

    private MembershipActivityStatus askForMembershipType() {
        while (true) {
            System.out.println("Medlemskab:");
            System.out.println("1. Aktivt");
            System.out.println("2. Passivt");

            int choice = readInt("Valg: ");
            switch (choice) {
                case 1 -> {
                    return MembershipActivityStatus.ACTIVE;
                }
                case 2 -> {
                    return MembershipActivityStatus.PASSIVE;
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
                    return new ActivityType.Recreational();
                }
                case 2 -> {
                    return new ActivityType.Competitive(new HashSet<>());
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }

    enum MembershipActivityStatus {
        ACTIVE,
        PASSIVE
    }
}
