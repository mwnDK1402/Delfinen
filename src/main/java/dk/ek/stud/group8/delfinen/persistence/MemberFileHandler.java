package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public final class MemberFileHandler {
    private final Path fileName;
    private int skippedLineCount;

    public MemberFileHandler(Path fileName) {
        this.fileName = Objects.requireNonNull(fileName);
    }

    public List<Member> load(LocalDate now) throws IOException {
        ArrayList<Member> members = new ArrayList<>();
        skippedLineCount = 0;

        try (Stream<String> lines = Files.lines(fileName).skip(1)) {
            lines.forEach(row -> {
                Member member = MemberCodec.decode(now, row);
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
            writer.write(MemberCodec.header());
            writer.newLine();
            for (Member m : members) {
                writer.write(MemberCodec.encode(m));
                writer.newLine();
            }
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
}
