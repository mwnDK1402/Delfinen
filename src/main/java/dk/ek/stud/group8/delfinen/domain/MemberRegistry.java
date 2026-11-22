package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class MemberRegistry {
    private final Random rng;
    private final Map<MemberId, Member> membersById;

    public MemberRegistry(Random rng) {
        this.rng = rng;
        this.membersById = new HashMap<>();
        // TODO: Load members from CSV file
    }

    public Member register(String name, LocalDate birthDate) {
        var id = nextId();
        var member = new Member(name, birthDate, id);
        membersById.put(id, member);
        return member;
    }

    private MemberId nextId() {
        var range = IdRange.forMemberCount(membersById.size());

        MemberId id;
        do {
            id = new MemberId(rng.nextInt(range.min(), range.max()));
        } while (membersById.containsKey(id));

        return id;
    }
}
