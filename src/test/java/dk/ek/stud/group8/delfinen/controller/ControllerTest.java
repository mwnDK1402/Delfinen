package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("US3 - Kontingentindtægt og kontingentstatus")
class ControllerTest {
    MemberRegistry memberRegistry;
    TreasurerRules treasurerRules;
    Controller controller;

    final LocalDate VALID_DATE = LocalDate.of(2002, 5, 6);
    final LocalDate now = LocalDate.of(2026, 1, 1);
    final String VALID_NAME = "TestName";
    final MembershipType PASSIVE = new MembershipType.Passive();

    @BeforeEach
    void setUp() {
        memberRegistry = new MemberRegistry(new Random());
        treasurerRules = new TreasurerRules(memberRegistry);
        controller = new Controller(memberRegistry, treasurerRules);
    }

    @Nested
    @DisplayName("Samlet kontingentindtægt")
    class ExpectedTotalIncome {
        @DisplayName("Beregner forventet samlet kontingentindtægt for 100 medlemmer")
        @Test
        void shouldCalculateExpectedTotalIncome_For100Members() {
            // Arrange
            int expectedTotal = 0;

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

            // Act
            int actualTotal = controller.getExpectedTotalIncome();

            // Assert
            assertEquals(expectedTotal, actualTotal);
        }
    }

    @Nested
    @DisplayName("Kontingentstatus")
    class MembershipFeeStatus {
        @DisplayName("Markerer et medlems kontingent som betalt")
        @Test
        void shouldMarkMembershipFeeAsPaid() {
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

        @DisplayName("Forhindrer registrering af kontingent, der allerede er betalt")
        @Test
        void shouldThrowException_whenMarkingAlreadyPaidMembershipFee() {
            // Arrange
            Member member = memberRegistry.register(
                    now,
                    now,
                    VALID_NAME,
                    VALID_DATE,
                    PASSIVE);

            controller.markAsPaid(member.getMemberId());

            // Act + Assert
            assertThrows(IllegalStateException.class,
                    () -> controller.markAsPaid(member.getMemberId()));
        }

        @DisplayName("Medlems-ID, der ikke findes, afvises")
        @Test
        void shouldThrowException_whenMemberDoesNotExist() {
            // Arrange
            MemberId invalidId = new MemberId(99);

            // Act + Assert
            assertThrows(IllegalArgumentException.class,
                    () -> controller.markAsPaid(invalidId));
        }
    }


}