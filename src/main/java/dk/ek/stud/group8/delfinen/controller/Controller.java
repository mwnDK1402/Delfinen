package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {
    // TODO: private final DataStorage storage;
    private final MemberRegister memberRegister;

    public Controller(MemberRegister memberRegister) {
        // TODO: this.storage = new DataStorage("data");
        // TODO: this.memberRegister = storage.loadAll();
        this.memberRegister = Objects.requireNonNull(memberRegister);
    }

    public void addMember(
            String name,
            LocalDate birthDate,
            MembershipType membershipType,
            ActivityType activityType,
            ArrayList<Discipline> disciplines,
            LocalDate now) {
        memberRegister.addMember(name, birthDate, membershipType, activityType, disciplines, now);
        // TODO: storage.saveAll();
    }

    public ArrayList<Member> getMembers() {
        return memberRegister.getMembers();
    }

    // TODO:
    /*public String getStorageWarnings() {
        return storage.getWarnings();
    }*/
}
