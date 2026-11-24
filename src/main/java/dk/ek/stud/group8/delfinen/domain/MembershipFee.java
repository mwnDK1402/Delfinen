package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;

/**
 * Medlemskabskontingent for en periode (Danish: <i>Kontingent</i>)
 */
public class MembershipFee {
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private boolean paid;

    public MembershipFee(LocalDate now, Member member) {
        startDate = now;
        endDate = now.plusYears(1);
        amount = 1600;
        paid = false;
    }
}
