package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MemberId;
import dk.ek.stud.group8.delfinen.domain.MembershipType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US14 - DSV-format for medlemmer")
final class MemberCodecTest {
    private static final LocalDate now = LocalDate.of(2026, 1, 1);

    private static final Member testMember = new Member(
            now,
            new MemberId(5),
            "Mathias",
            LocalDate.of(1999, 2, 14),
            new MembershipType.Passive(),
            LocalDate.of(2025, 1, 1));

    @Nested
    @DisplayName("Gyldig")
    class Valid {
        @Test
        void shouldReturnHeaders_inExactFormat() {
            // Arrange
            var expectedHeaders = "memberId\tname\tbirthDate\tactive\tactivityType\tdisciplines\tjoinDate";

            // Act
            var actualHeaders = MemberCodec.header();

            // Assert
            assertEquals(expectedHeaders, actualHeaders);
        }

        @DisplayName("Medlem bliver skrevet som TSV-række")
        @Test
        void shouldReturnRow_givenValidMember() {
            // Arrange

            // Act
            var row = MemberCodec.encode(testMember);

            // Assert
            assertEquals("5\tMathias\t1999-02-14\tfalse\tNONE\t\t2025-01-01", row);
        }

        @DisplayName("Medlemsrække bliver indlæst som medlem")
        @Test
        void shouldReturnMember_givenValidRow() {
            // Arrange
            var row = "5\tMathias\t1999-02-14\tfalse\tNONE\t\t2025-01-01";

            // Act
            var member = MemberCodec.decode(now, row);

            // Assert
            assertEquals(testMember, member);
        }
    }

    @DisplayName("Ugyldige")
    @Nested
    class Invalid {
        @DisplayName("Medlemsrække uden navn er null")
        @Test
        void shouldDecodeAsNull_whenNameIsBlank() {
            // Arrange
            var row = "5\t\t1999-02-14\tfalse\tNONE\t\t2025-01-01";

            // Act
            var shouldBeNull = MemberCodec.decode(now, row);

            // Assert
            assertNull(shouldBeNull);
        }

        @DisplayName("Medlemsrække med fødseldag i fremtiden er null")
        @Test
        void shouldDecodeAsNull_whenBirthDateInFuture() {
            // Arrange
            var row = "5\t\t2027-01-01\tfalse\tNONE\t\t2025-01-01";

            // Act
            var shouldBeNull = MemberCodec.decode(now, row);

            // Assert
            assertNull(shouldBeNull);
        }

        @DisplayName("Medlemsrække med registreringdato i fremtiden er null")
        @Test
        void shouldDecodeAsNull_whenJoinDateInFuture() {
            // Arrange
            var row = "5\t\t1999-02-14\tfalse\tNONE\t\t2027-01-01";

            // Act
            var shouldBeNull = MemberCodec.decode(now, row);

            // Assert
            assertNull(shouldBeNull);
        }

        @DisplayName("Medlemsrække med aktivt medlem men uden aktivitetsform er null")
        @Test
        void shouldDecodeAsNull_whenActiveWithoutActivityType() {
            // Arrange
            var row = "5\tMathias\t1999-02-14\ttrue\tNONE\t\t2025-01-01";

            // Act
            var shouldBeNull = MemberCodec.decode(now, row);

            // Assert
            assertNull(shouldBeNull);
        }

        @DisplayName("Medlemsrække med aktivt medlem men uden aktivitetsform er null")
        @Test
        void shouldDecodeAsNull_whenCompetitiveWithoutDiscipline() {
            // Arrange
            var row = "5\tMathias\t1999-02-14\ttrue\tCOMPETITIVE\t\t2025-01-01";

            // Act
            var shouldBeNull = MemberCodec.decode(now, row);

            // Assert
            assertNull(shouldBeNull);
        }
    }
}