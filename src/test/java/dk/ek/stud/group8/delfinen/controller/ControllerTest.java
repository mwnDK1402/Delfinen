package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @DisplayName("Beregner forventet samlet kontingentindtægt for 100 medlemmer")
    @Test
    void getExpectedTotalIncome() {
        // Arrange
        MemberRegistry memberRegistry = new MemberRegistry(new Random());
        Controller controller = new Controller(memberRegistry);

        LocalDate now = LocalDate.now();

        int expectedTotal = 0;

        // Act
        for(int i = 0; i < 100; i++) {

            MembershipType membershipType;

            if (i < 50) {
                membershipType = new MembershipType.Passive();
            } else {
                membershipType = new MembershipType.Active(
                        new ActivityType.Recreational());
            }
            Member member = memberRegistry.register(
                    now,
                    now,
                    "Bob",
                    LocalDate.of(2000, 5, 5),
                    membershipType);

            expectedTotal += member.getMembershipFee().getAmount();
        }

        // Assert
        assertEquals(expectedTotal, controller.getExpectedTotalIncome());
    }
}