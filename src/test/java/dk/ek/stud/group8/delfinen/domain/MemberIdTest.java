package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US1 - Medlemsnumre er unikke")
class MemberIdTest {
    private static final Set<Integer> oneToFive = Set.of(
            1, 2, 3, 4, 5
    );

    private MemberId.Factory oneToFiveFactory;

    @BeforeEach
    void setup() {
        oneToFiveFactory = new MemberId.Factory(new Random(), oneToFive);
    }

    @DisplayName("Medlemsnumre genereres inden for dette interval for det angivne antal")
    @ParameterizedTest(name = "{0} numre: {1}..<{2}")
    @CsvSource(value = {
            "0,0,10000", "999,0,10000",
            "1000,1000,100000", "9999,1000,100000",
            "10000,10000,1000000", "99999,10000,1000000",
            "100000,100000,10000000", "999999,100000,10000000"
    })
    void shouldReturnRange_givenActiveCount(int activeCount, int expectedMin, int expectedMax) {
        var range = MemberId.Factory.IdRange.forActiveCount(activeCount);

        assertEquals(expectedMin, range.min());
        assertEquals(expectedMax, range.max());
    }

    @Nested
    class Valid {
        @Disabled
        @Test
        @DisplayName("Et sæt af integers kan sammenlignes med medlemsnumre")
        void shouldEqual_whenComparingSetsOfMemberIdsAndIntegers() {
            Set<MemberId> allIds = oneToFive.stream()
                    .map(MemberId::new)
                    .collect(Collectors.toUnmodifiableSet());

            assertTrue(allIds.containsAll(Set.of(1, 2, 3, 4, 5)));
        }
    }

    @Nested
    class Invalid {
        @Test
        @DisplayName("Samme nummer indlæst to gange bliver afvist")
        void shouldThrow_whenIdLoadedTwice() {
            // Act
            oneToFiveFactory.load(1);
            assertThrows(IllegalArgumentException.class, () -> oneToFiveFactory.load(1));
        }
    }
}