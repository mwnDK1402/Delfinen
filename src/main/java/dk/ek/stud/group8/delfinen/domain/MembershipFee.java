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
        paid = false;

        if (member.isPassive()) {
            amount = 500;
        } else if (member.getSeniority(now).equals(Seniority.Junior)) {
            amount = 1000;
        } else if (member.getSeniority(now).equals(Seniority.Senior) && member.getAgeInYears(now) <= 59) {
            amount = 1600;
        } else if (member.getSeniority(now).equals(Seniority.Senior) && member.getAgeInYears(now) >= 60) {
            amount = 1200;
        }
    }

    public int getAmount() {
        return amount;
    }
}
