package dk.ek.stud.group8.delfinen.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("US14 - Datoformat")
class FileDatesTest {
    @DisplayName("Datoer indlæses uden ændringer")
    @ParameterizedTest(name = "Datoen {0} bliver indlæst uden ændring")
    @CsvSource(value = {
            "1999-12-31",
            "2000-01-01",
            "0000-01-01",
            "3000-01-01",
    })
    void shouldRoundtrip_givenValid_ISO8601_Dates(LocalDate valid) {
        // Act
        var cell = FileDates.toCell(valid);
        var loadedDate = FileDates.fromCell(cell);

        // Assert
        assertEquals(valid, loadedDate);
    }

    @DisplayName("Ugyldige datoer bliver afvist")
    @ParameterizedTest(name = "{0} afvises")
    @ValueSource(strings = {
            "1999/12/31",
            "31-12-1999",
            "12-31-1999",
            "1999 12 31",
            "December 31 1999",
            "1999-12-32",
            "",
    })
    void shouldThrow_givenInvalidDates(String invalid) {
        // Act + Assert
        assertThrows(DateTimeParseException.class, () -> FileDates.fromCell(invalid));
    }
}