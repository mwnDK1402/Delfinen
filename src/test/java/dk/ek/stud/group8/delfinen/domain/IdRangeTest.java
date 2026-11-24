package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Medlemsnumre")
class IdRangeTest {
    @DisplayName("Genereres inden for dette interval for det angivne antal medlemmer")
    @ParameterizedTest(name = "{0} numre: {1}..<{2}")
    @CsvSource(value = {
            "0,1,10000", "999,1,10000",
            "1000,1000,100000", "9999,1000,100000",
            "10000,10000,1000000", "99999,10000,1000000",
            "100000,100000,10000000", "999999,100000,10000000"
    })
    void shouldReturnRange_givenMemberCount(int memberCount, int expectedMin, int expectedMax) {
        var range = IdRange.forMemberCount(memberCount);

        assertEquals(expectedMin, range.min());
        assertEquals(expectedMax, range.max());
    }
}
