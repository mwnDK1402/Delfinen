package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controller {
    // TODO: private final DataStorage storage;
    private final MemberRegistry memberRegistry;

    public Controller(MemberRegistry memberRegistry) {
        // TODO: this.storage = new DataStorage("data");
        // TODO: this.memberRegister = storage.loadAll();
        this.memberRegistry = Objects.requireNonNull(memberRegistry);
    }

    public void addMember(
            LocalDate joinDate,
            String name,
            LocalDate birthDate,
            MembershipType membershipType,
            ActivityType activityType,
            List<Discipline> disciplines) {
        memberRegistry.register(joinDate, name, birthDate, membershipType, activityType, disciplines);
        // TODO: storage.saveAll();
    }

    public List<Member> getMembers() {
        return memberRegistry.getMembers();
    }

    // TODO:
    /*public String getStorageWarnings() {
        return storage.getWarnings();
    }*/
}
