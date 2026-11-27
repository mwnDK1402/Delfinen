package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controller {
    // TODO: private final DataStorage storage;
    private final MemberRegistry memberRegistry;
    private final TreasurerRules treasurerRules;

    public Controller(MemberRegistry memberRegistry, TreasurerRules treasurerRules) {
        // TODO: this.storage = new DataStorage("data");
        // TODO: this.memberRegister = storage.loadAll();
        this.memberRegistry = Objects.requireNonNull(memberRegistry);
        this.treasurerRules = Objects.requireNonNull(treasurerRules);
    }

    public void addMember(
            LocalDate now,
            LocalDate joinDate,
            String name,
            LocalDate birthDate,
            MembershipType membershipType) {
        memberRegistry.register(now, joinDate, name, birthDate, membershipType);
        // TODO: storage.saveAll();
    }

    public List<Member> getMembers() {
        return memberRegistry.getMembers();
    }

    public int getExpectedTotalIncome() {
        return treasurerRules.calculateTotalIncome();
    }

    public void markAsPaid(MemberId id) {
        Member member = memberRegistry.findMemberById(id);

        if(member == null) {
            throw new IllegalArgumentException("Member not found.");
        }

        member.getMembershipFee().markAsPaid();
    }

    // TODO:
    /*public String getStorageWarnings() {
        return storage.getWarnings();
    }*/
}
