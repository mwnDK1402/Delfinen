package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US1 - Tilføj medlemmer")
class MemberTest {
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
                "Simon"
        })
        void shouldConstruct_whenNameIsNotBlank(String name) {
            // Arrange
            final var birthDate = LocalDate.of(2000, 1, 1);

            // Act + Assert
            assertDoesNotThrow(() -> {
                new Member(name, birthDate);
            });
        }
    }

    @Nested
    @DisplayName("Ugyldig")
    class Invalid {
        @Test
        @DisplayName("Fødselsdag i fremtiden afvises")
        void shouldThrow_whenBirthDateInFuture() {
            // Arrange
            final var now = LocalDate.now();
            final var future = now.plusDays(1);

            // Act + Assert
            assertThrows(Member.FutureBirthDateException.class, () -> {
                new Member("Simon", future);
            });
        }
    }
}
