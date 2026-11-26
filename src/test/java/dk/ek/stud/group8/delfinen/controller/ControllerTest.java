package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    MemberRegistry memberRegistry;
    Controller controller;

    final LocalDate VALID_DATE = LocalDate.of(2002, 5, 6);
    final LocalDate now = LocalDate.of(2026, 1, 1);
    final MemberId VALID_ID = new MemberId(1);
    final String VALID_NAME = "TestName";
    final MembershipType PASSIVE = new MembershipType.Passive();

    @BeforeEach
    void setUp() {
        memberRegistry = new MemberRegistry(new Random());
        controller = new Controller(memberRegistry);
    }

    @DisplayName("Beregner forventet samlet kontingentindtægt for 100 medlemmer")
    @Test
    void getExpectedTotalIncome() {
        // Arrange
        int expectedTotal = 0;

        // Act
        for (int i = 0; i < 100; i++) {

            MembershipType membershipType;

            if (i < 50) {
                membershipType = PASSIVE;
            } else {
                membershipType = new MembershipType.Active(
                        new ActivityType.Recreational());
            }
            Member member = memberRegistry.register(
                    now,
                    now,
                    VALID_NAME,
                    VALID_DATE,
                    membershipType);

            expectedTotal += member.getMembershipFee().getAmount();
        }

        // Assert
        assertEquals(expectedTotal, controller.getExpectedTotalIncome());
    }

    @DisplayName("Markerer et medlems kontingent som betalt")
    @Test
    void ShouldReturnIsPaidTrue_when_markedAsPaid() {
        // Arrange
        Member member = memberRegistry.register(
                now,
                now,
                VALID_NAME,
                VALID_DATE,
                PASSIVE);

        // Act
        controller.markAsPaid(member.getMemberId());

        // Assert
        assertTrue(member.getMembershipFee().getPaid());
    }
}