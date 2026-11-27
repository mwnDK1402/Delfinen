package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public final class MemberFileHandler {
    private static final String SEPARATOR = "\t";
    private static final String DISCIPLINE_SEPARATOR = ",";
    private static final List<String> HEADER_COLUMNS = List.of(
            "memberId",
            "name",
            "birthDate",    // YYYY-MM-DD
            "active",       // true/false
            "activityType", // null/Recreational/Competitive
            "disciplines",  // null/BUTTERFLY,CRAWL,BACKCRAWL,BREASTSTROKE
            "joinDate"      // YYYY-MM-DD
    );

    private final String fileName;
    private int skippedLineCount;

    public MemberFileHandler(String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
    }

    private static String header() {
        return String.join(SEPARATOR, HEADER_COLUMNS);
    }

    private static void skipHeader(Scanner fileScanner) {
        if (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
        }
    }

    private static Set<Discipline> parseDisciplines(String field) {
        HashSet<Discipline> result = new HashSet<>(4);
        String[] disciplines = field.split(DISCIPLINE_SEPARATOR, -1);
        for (String discipline : disciplines) {
            if (discipline.isBlank()) continue;
            result.add(Discipline.valueOf(discipline));
        }

        return result;
    }

    private static String writeDisciplines(Set<Discipline> disciplines) {
        // TODO: Isabella will rewrite this without ninja tricks
        return String.join(DISCIPLINE_SEPARATOR, disciplines.stream().map(Enum::name).toList());
    }

    private static MembershipType createMembershipType(boolean active, ActivityTypeRecord activityType, Set<Discipline> disciplines) {
        if (!active) {
            return new MembershipType.Passive();
        }

        if (activityType == null) {
            return null;
        }

        return switch (activityType) {
            case RECREATIONAL -> new MembershipType.Active(new ActivityType.Recreational());
            case COMPETITIVE -> new MembershipType.Active(new ActivityType.Competitive(disciplines));
        };
    }

    private static ActivityTypeRecord parseActivityType(String field) {
        return !field.isBlank()
                ? ActivityTypeRecord.valueOf(field.trim())
                : null;
    }

    private static String writeActivityType(ActivityType type) {
        switch (type) {
            case ActivityType.Competitive _ -> {
                return ActivityTypeRecord.COMPETITIVE.name();
            }
            case ActivityType.Recreational _ -> {
                return ActivityTypeRecord.RECREATIONAL.name();
            }
        }
    }

    public List<Member> load() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        skippedLineCount = 0;

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            skipHeader(fileScanner);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Member member = parseMember(line);
                if (member == null) {
                    skippedLineCount++;
                } else {
                    members.add(member);
                }
            }
        }

        return members;
    }

    public void save(List<Member> members) throws FileNotFoundException {
        PrintStream output = new PrintStream(fileName);
        output.println(header());

        for (Member m : members) {
            output.println(toCsvLine(m));
        }
        output.close();
    }

    private String toCsvLine(Member member) {
        MembershipType membershipType = member.getMembershipType();
        boolean isActive = membershipType instanceof MembershipType.Active;
        String activityType = switch (membershipType) {
            case MembershipType.Active active -> writeActivityType(active.type());
            case MembershipType.Passive _ -> "";
        };
        Set<Discipline> disciplines = switch (membershipType) {
            case MembershipType.Active active -> switch (active.type()) {
                case ActivityType.Competitive competitive -> competitive.disciplines();
                case ActivityType.Recreational _ -> Set.of();
            };
            case MembershipType.Passive _ -> Set.of();
        };
        return String.join(SEPARATOR,
                String.valueOf(member.getMemberId().value()),
                member.getName(),
                member.getBirthDate().toString(), // TODO: Standardize format
                String.valueOf(isActive),
                activityType,
                writeDisciplines(disciplines),
                member.getJoinDate().toString() // TODO: Standardize format
        );
    }

    /**
     * @return null on failure
     */
    private Member parseMember(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        if (fields.length != HEADER_COLUMNS.size()) {
            throw new IllegalArgumentException("forkert antal felter (" + fields.length + ")");
        }
        try {
            int memberId = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            LocalDate birthdayDate = LocalDate.parse(fields[2].trim());
            boolean active = Boolean.parseBoolean(fields[3].trim());
            // Hvis medlemmerne er passive, har de ingen aktivitetsform
            ActivityTypeRecord activityType = parseActivityType(fields[4]);
            Set<Discipline> disciplines = parseDisciplines(fields[5].trim());
            LocalDate joinDate = LocalDate.parse(fields[6]);

            return new Member(
                    LocalDate.now(),
                    new MemberId(memberId),
                    name,
                    birthdayDate,
                    createMembershipType(active, activityType, disciplines),
                    joinDate);

        } catch (IllegalArgumentException | DateTimeParseException e) {
            return null;
        }
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    public String getWarnings() {
        return skippedLineCount > 0 ? "Skipped " + skippedLineCount + " rows" : "";
    }

    private enum ActivityTypeRecord {
        RECREATIONAL,
        COMPETITIVE
    }
}
