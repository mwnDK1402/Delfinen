package dk.ek.stud.group8.delfinen.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kontingent")
class MembershipFeeTest {
    private static final LocalDate now = LocalDate.of(2026, 1, 1);

    @ParameterizedTest
    @CsvSource(value = {
            "500,0",
            "1000,16",
            "1600,27",
            "1200,65",
    })
    void shouldHaveAmount_givenSeniority(int expectedAmount, int age) {
        // Arrange
        var birthDate = now.minusYears(age);
        var activityType = age != 0 ? ActivityType.Recreational : null;
        var member = new Member(now, "TestName", birthDate, new MemberId(1), activityType);
        var fee = new MembershipFee(now, member);

        // Act
        var amount = fee.getAmount();

        // Assert
        assertEquals(expectedAmount, amount);
    }
}