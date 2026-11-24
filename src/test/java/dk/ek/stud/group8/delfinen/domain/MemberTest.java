package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US1 - Tilføj medlemmer")
class MemberTest {
    private static final LocalDate VALID_DATE =
            LocalDate.of(2002, 5, 6);
    private static final MemberId VALID_ID = new MemberId(1);
    private final String VALID_NAME = "Simon";

    @Nested
    @DisplayName("Gyldig")
    class Valid {
        @ParameterizedTest(name = "{0} accepteres")
        @DisplayName("Navne")
        @ValueSource(strings = {
                "-",
                ",",
                "æøæaåæåøæø",
                "Isabella",
                "Mathias",
                "Simon",
                "smimon"
        })
        void shouldConstruct_whenNameIsNotBlank(String name) {
            // Act + Assert
            assertDoesNotThrow(() -> {
                new Member(name, VALID_DATE, VALID_ID, null);
            });
        }

        @Test
        @DisplayName("Fødselsdag i dag accepteres")
        void shouldConstruct_whenBirthDateToday() {
            // Arrange
            final var now = LocalDate.now();

            // Act + Assert
            assertDoesNotThrow(() -> new Member(VALID_NAME, now, VALID_ID, null));
        }
    }

    @Nested
    @DisplayName("Ugyldig")
    class Invalid {
        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("null navn afvises")
        void shouldThrow_whenNameIsNull() {
            // Act
            assertThrows(NullPointerException.class, () -> new Member(null, VALID_DATE, VALID_ID, null));
        }

        @ParameterizedTest(name = "{0} afvises")
        @DisplayName("Tomt navn afvises")
        @ValueSource(strings = {
                "",
                " ",
        })
        void shouldThrow_whenNameIsBlank(String name) {
            // Act + Assert
            assertThrows(Member.BlankNameException.class, () -> new Member(name, VALID_DATE, VALID_ID, null));
        }

        @Test
        @DisplayName("Fødselsdag i morgen afvises")
        void shouldThrow_whenBirthDateTomorrow() {
            // Arrange
            final var now = LocalDate.now();
            final var future = now.plusDays(1);

            // Act + Assert
            assertThrows(Member.FutureBirthDateException.class, () ->
                    new Member(VALID_NAME, future, VALID_ID, null));
        }
    }

    @Nested
    @DisplayName("Medlemmets alder")
    class Age {
        @ParameterizedTest(name = "Hvis dags dato er {2} og man er født {1}, så er man {0} år")
        @CsvSource(value = {
                "27,1999-02-14,2026-11-24",
                "26,1999-02-14,2026-01-01"
        })
        @DisplayName("Aldre passer med fødselsdagsdato")
        void shouldReturnAge_givenBirthDateAndCurrentDate(
                int expectedAgeInYears, LocalDate birthDate, LocalDate currentDate) {

            // Arrange
            Member member = new Member("TestName", birthDate, new MemberId(5), null);

            // Act
            var age = member.getAgeInYears(currentDate);

            // Assert
            assertEquals(expectedAgeInYears, age);
        }
    }
}
