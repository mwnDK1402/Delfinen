package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("US1 - Tilføj medlemmer")
class MemberTest {
    private final String VALID_NAME = "Simon";
    private static final LocalDate VALID_DATE =
            LocalDate.of(2002, 5, 6);

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
                new Member(name, VALID_DATE);
            });
        }

        @Test
        @DisplayName("Fødselsdag i dag accepteres")
        void shouldConstruct_whenBirthDateToday() {
            // Arrange
            final var now = LocalDate.now();

            // Act + Assert
            assertDoesNotThrow(() -> new Member(VALID_NAME, now));
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
            assertThrows(NullPointerException.class, () -> new Member(null, VALID_DATE));
        }

        @ParameterizedTest(name = "{0} afvises")
        @DisplayName("Tomt navn afvises")
        @ValueSource(strings = {
                "",
                " ",
        })
        void shouldThrow_whenNameIsBlank(String name) {
            // Act + Assert
            assertThrows(Member.BlankNameException.class, () -> new Member(name, VALID_DATE));
        }

        @Test
        @DisplayName("Fødselsdag i morgen afvises")
        void shouldThrow_whenBirthDateTomorrow() {
            // Arrange
            final var now = LocalDate.now();
            final var future = now.plusDays(1);

            // Act + Assert
            assertThrows(Member.FutureBirthDateException.class, () ->
                    new Member(VALID_NAME, future));
        }
    }
}
