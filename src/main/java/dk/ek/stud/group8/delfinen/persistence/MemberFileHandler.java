package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

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
            // TODO: Add training results (potentially in another TSV file?)
            "joinDate"      // YYYY-MM-DD
    );

    private final Path fileName;
    private int skippedLineCount;

    public MemberFileHandler(Path fileName) {
        this.fileName = Objects.requireNonNull(fileName);
    }

    private static String header() {
        return String.join(SEPARATOR, HEADER_COLUMNS);
    }

    private static Map<Discipline, TrainingResult> parseDisciplines(String field) {
        HashMap<Discipline, TrainingResult> result = new HashMap<>(4);
        String[] disciplines = field.split(DISCIPLINE_SEPARATOR, -1);
        for (String discipline : disciplines) {
            if (discipline.isBlank()) continue;
            // TODO: Load training results by discipline
            result.put(Discipline.valueOf(discipline), TrainingResult.NONE);
        }

        return result;
    }

    private static String writeDisciplines(Map<Discipline, TrainingResult> bestByDiscipline) {
        // TODO(#37): Rewrite this without ninja tricks
        return String.join(DISCIPLINE_SEPARATOR,
                bestByDiscipline.keySet()
                        .stream()
                        .map(Enum::name)
                        .toList());
    }

    private static MembershipType createMembershipType(
            boolean active,
            ActivityTypeRecord activityType,
            Map<Discipline, TrainingResult> bestByDiscipline) {
        if (!active) {
            return new MembershipType.Passive();
        }

        if (activityType == null) {
            return null;
        }

        return switch (activityType) {
            case RECREATIONAL -> new MembershipType.Active(new ActivityType.Recreational());
            case COMPETITIVE -> new MembershipType.Active(new ActivityType.Competitive(bestByDiscipline));
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

    public List<Member> load() throws IOException {
        ArrayList<Member> members = new ArrayList<>();
        skippedLineCount = 0;

        try (Stream<String> lines = Files.lines(fileName).skip(1)) {
            lines.forEach(line -> {
                Member member = parseMember(line);
                if (member == null) {
                    skippedLineCount++;
                } else {
                    members.add(member);
                }
            });
        } catch (NoSuchFileException _) {
            // Simply start with no members when no file exists
        }

        return members;
    }

    public void save(List<Member> members) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(fileName)) {
            writer.write(header());
            writer.newLine();
            for (Member m : members) {
                writer.write(toTsvLine(m));
                writer.newLine();
            }
        }
    }

    private String toTsvLine(Member member) {
        MembershipType membershipType = member.getMembershipType();
        boolean isActive = membershipType instanceof MembershipType.Active;
        String activityType = switch (membershipType) {
            case MembershipType.Active active -> writeActivityType(active.type());
            case MembershipType.Passive _ -> "";
        };
        Map<Discipline, TrainingResult> disciplines = switch (membershipType) {
            case MembershipType.Active active -> switch (active.type()) {
                case ActivityType.Competitive competitive -> competitive.bestByDiscipline();
                case ActivityType.Recreational _ -> Map.of();
            };
            case MembershipType.Passive _ -> Map.of();
        };
        return String.join(SEPARATOR,
                String.valueOf(member.getMemberId().value()),
                member.getName(),
                FileDates.toCell(member.getBirthDate()),
                String.valueOf(isActive),
                activityType,
                writeDisciplines(disciplines),
                FileDates.toCell(member.getJoinDate())
        );
    }

    /**
     * @return null on failure
     */
    private Member parseMember(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        if (fields.length != HEADER_COLUMNS.size()) {
            return null;
        }
        try {
            int memberId = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            LocalDate birthdayDate = FileDates.fromCell(fields[2].trim());
            boolean active = Boolean.parseBoolean(fields[3].trim());
            // Hvis medlemmerne er passive, har de ingen aktivitetsform
            ActivityTypeRecord activityType = parseActivityType(fields[4]);
            Map<Discipline, TrainingResult> bestByDisciplines = parseDisciplines(fields[5].trim());
            LocalDate joinDate = FileDates.fromCell(fields[6]);

            return new Member(
                    LocalDate.now(),
                    new MemberId(memberId),
                    name,
                    birthdayDate,
                    createMembershipType(active, activityType, bestByDisciplines),
                    joinDate);

        } catch (IllegalArgumentException | DateTimeParseException e) {
            return null;
        }
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    public String getWarnings() {
        return switch (skippedLineCount) {
            case 0 -> "";
            case 1 -> "Data for ét medlem var korrupt og kunne ikke indlæses";
            default -> "Data for " + skippedLineCount + " medlemmer var korrupt og kunne ikke indlæses";
        };
    }

    private enum ActivityTypeRecord {
        RECREATIONAL,
        COMPETITIVE
    }
}
