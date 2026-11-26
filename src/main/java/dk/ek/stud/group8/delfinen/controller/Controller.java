package dk.ek.stud.group8.delfinen.controller;

import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.MemberRegistry;
import dk.ek.stud.group8.delfinen.domain.MembershipType;
import dk.ek.stud.group8.delfinen.persistence.DataStorage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controller {
    private final MemberRegistry memberRegistry;
    private final DataStorage storage;

    public Controller(MemberRegistry memberRegistry, DataStorage storage) {
        this.memberRegistry = Objects.requireNonNull(memberRegistry);
        this.storage = Objects.requireNonNull(storage);
    }

    public void loadAll() throws IOException {
        storage.loadAll();
    }

    public void saveAll() throws IOException {
        storage.saveAll();
    }

    public void addMember(
            LocalDate now,
            LocalDate joinDate,
            String name,
            LocalDate birthDate,
            MembershipType membershipType) {
        memberRegistry.register(now, joinDate, name, birthDate, membershipType);
    }

    public List<Member> getMembers() {
        return memberRegistry.getMembers();
    }

    public boolean hasStorageWarnings() {
        return storage.hasWarnings();
    }

    public String getStorageWarnings() {
        return storage.getWarnings();
    }
}
