package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    // private final DataStorage storage;
    private MemberRegister memberRegister;

    public Controller() {
        /*this.storage = new DataStorage("data");
        this.memberRegister = storage.loadAll();*/
        memberRegister = new MemberRegister();
    }

    public void addMember(String name, LocalDate birthDate, MembershipType membershipType,
                          ActivityType activityType, ArrayList<Discipline> disciplines, LocalDate now) {
        memberRegister.addMember(name, birthDate, membershipType, activityType, disciplines, LocalDate.now());
        // storage.saveAll();
    }

    public ArrayList<Member> getMembers() {
        return memberRegister.getMembers();
    }

    /*public String getStorageWarnings() {
        return storage.getWarnings();
    }*/
}
