package dk.ek.stud.group8.delfinen.domain;

public class TreasurerRules {
    private final MemberRegistry memberRegistry;

    public TreasurerRules(MemberRegistry memberRegistry) {
        this.memberRegistry = memberRegistry;
    }

    public int calculateTotalIncome() {
        int total = 0;

        for(Member member : memberRegistry.getMembers()) {
            total += member.getMembershipFee().getAmount();
        }
        return total;
    }
}
