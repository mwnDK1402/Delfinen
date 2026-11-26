package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("US14 - Filhåndtering af medlemmer")
final class MemberFileHandlerTest {
    private static final LocalDate now = LocalDate.of(2026, 1, 1);

    @TempDir
    private Path tempDir;

    @Test
    @DisplayName("Gemte medlemmer bliver indlæst")
    void shouldLoadSameMembers_afterSavingFile() throws IOException {
        // Arrange
        var members = List.of(
                new Member(
                        now,
                        new MemberId(1),
                        "Mathias",
                        LocalDate.of(1999, 2, 14),
                        new MembershipType.Active(new ActivityType.Competitive(Set.of(
                                Discipline.BUTTERFLY,
                                Discipline.BACKCRAWL
                        ))),
                        now),
                new Member(
                        now,
                        new MemberId(2),
                        "Isabella",
                        LocalDate.of(2002, 5, 6),
                        new MembershipType.Passive(),
                        now)
        );
        Path tsvFile = tempDir.resolve("save_then_load.tsv");
        var saveHandler = new MemberFileHandler(tsvFile);
        var loadHandler = new MemberFileHandler(tsvFile);

        // Act
        saveHandler.save(members);
        System.out.println(Files.readString(tsvFile));
        var loaded = loadHandler.load();

        // Assert
        assertEquals(new HashSet<>(members), new HashSet<>(loaded), loadHandler.getWarnings());
        assertEquals(members.size(), loaded.size());
    }
}