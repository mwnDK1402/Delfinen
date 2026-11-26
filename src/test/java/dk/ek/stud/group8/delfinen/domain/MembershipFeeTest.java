package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US1 - Kontingent")
class MembershipFeeTest {
    private static final LocalDate now = LocalDate.of(2026, 1, 1);

    @Test
    @DisplayName("Passivt medlemskab koster 500 kr.")
    void shouldHaveAmount_whenMembershipIsPassive() {
        // Arrange
        var member = arrangeMember(0, new MembershipType.Passive());
        var fee = new MembershipFee(now, member);

        // Act
        var amount = fee.getAmount();

        // Assert
        assertTrue(member.isPassive(), "Medlemmet skal være passivt");
        assertEquals(500, amount);
    }

    @ParameterizedTest(name = "Medlem på {1} år betaler {0} kr.")
    @CsvSource(value = {
            "1000,0",
            "1000,17",
            "1600,18",
            "1600,59",
            "1200,60",
    })
    @DisplayName("Aktive medlemskaber")
    void shouldHaveAmount_givenAge(int expectedAmount, int age) {
        // Arrange
        var membership = new MembershipType.Active(new ActivityType.Recreational());
        var member = arrangeMember(age, membership);
        var fee = new MembershipFee(now, member);

        // Act
        var amount = fee.getAmount();

        // Assert
        assertFalse(member.isPassive(), "Medlemmet skal være aktivt");
        assertEquals(expectedAmount, amount);
    }

    private static Member arrangeMember(int age, MembershipType membershipType) {
        String name = "TestName";
        var birthDate = now.minusYears(age);
        MemberId id = new MemberId(1);
        return new Member(now, id, name, birthDate, membershipType, now);
    }
}
