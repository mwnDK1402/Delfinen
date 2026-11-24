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
        } else if (member.getSeniority(now) == Seniority.Junior) {
            amount = 1000;
        } else if (member.getAgeInYears(now) >= 60) {
            amount = 1200;
        } else {
            amount = 1600;
        }
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid() {
        this.paid = paid;
    }
}
