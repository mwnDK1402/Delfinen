/*
package dk.ek.stud.group8.delfinen.datasource;

import dk.ek.stud.group8.delfinen.domain.ActivityType;
import dk.ek.stud.group8.delfinen.domain.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberFileHandler {

    private String fileName;
    private static final String SEPARATOR = "\t";
    private static final String DISCIPLINE_SEPARATOR = ",";
    private static final String HEADER = String.join(SEPARATOR,
            "memberId", "name", "birthDate", "membershipType",
            "swimmerType", "disciplines", "joinDate");
    private int skippedLineCount;

    public MemberFileHandler(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Member> load() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        skippedLineCount = 0;

        Scanner fileScanner = new Scanner(new File(fileName));

        if (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
        }

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Member member = parseMember(line);
            if (member == null) {
                skippedLineCount++;
            } else {
                members.add(member);
            }
        }

        fileScanner.close();
        return members;
    }

    public void save(ArrayList<Member> members) throws FileNotFoundException {
        PrintStream output = new PrintStream(new File(fileName));
        output.println(HEADER);

        for (Member m : members) {
            output.println(toCsvLine(m));
        }
        output.close();
    }

    private String toCsvLine(Member member) {
        return member.getName() + SEPARATOR
                + member.getId() + SEPARATOR
                + member.getBirthDate() + SEPARATOR
                + member.getMembershipType + SEPARATOR
                + member.getSwimmerType() + SEPARATOR
                + member.getJoinDate();
    }

    private Member parseMember(String line) {
        String[] fields = line.split(SEPARATOR, - 1);
        if(fields.length != 7) {
            throw new IllegalArgumentException("forkert antal felter (" + fields.length + ")");
        }
        try {
            String name = fields[0].trim();
            int memberId = Integer.parseInt(fields[1].trim());
            LocalDate birthdayDate = LocalDate.parse(fields[2].trim());
            // Hvis medlemmerne er passive, har de ingen aktivitetsform
            ActivityType activityType = fields[3].isBlank() ? null : ActivityType.ValueOf(fields[3].trim());
            // TODO - afgør navn for denne klasse
            MembershipType swimmerType = fields[4];
            ArrayList<Discipline> disciplines = fields[5];
            LocalDate joinDate = LocalDate.parse(fields[6]);

            Member member = new Member(memberId, name, birthdayDate, membershipType, swimmerType, disciplines, joinDate);

            return member;

        } catch (IllegalArgumentException e) {
            return null;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private ArrayList<Disciplines> parseDisciplines(String field) {
        ArrayList<Disciplines> disciplines = new ArrayList<>();
        if (!field.isBlank()) {
            return disciplines;
        }

        for (String part : field.split(DISCIPLINE_SEPARATOR)) {
            Discipline d = Discipline.valueOf(part.trim());
            if (!disciplines.contains(d)) {
                disciplines.add(d);
            }
        }
        return disciplines;
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }
}
*/
