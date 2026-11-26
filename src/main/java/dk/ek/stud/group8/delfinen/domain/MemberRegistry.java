package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.*;

public final class MemberRegistry {
    private final Random rng;
    private final Map<MemberId, Member> membersById;

    public MemberRegistry(Random rng) {
        this.rng = rng;
        this.membersById = new HashMap<>();
    }

    /**
     * Print a number of member ids padded to 4 digits.
     */
    static void main() {
        MemberRegistry memberRegistry = new MemberRegistry(new Random());

        for (int i = 0; i < 100; i++) {
            Member member = memberRegistry.register(
                    LocalDate.now(),
                    LocalDate.now(),
                    "Tobias",
                    LocalDate.of(2000, 5, 5),
                    new MembershipType.Passive());
            System.out.printf("%04d%n", member.getMemberId().value());
        }
    }

    public void load(List<Member> loaded) {
        for (var member : loaded) {
            membersById.put(member.getMemberId(), member);
        }
    }

    public Member register(
            LocalDate now,
            LocalDate joinDate,
            String name,
            LocalDate birthDate,
            MembershipType membershipType) {
        var id = nextId();
        Member member = new Member(
                now,
                id,
                name,
                birthDate,
                membershipType,
                joinDate);
        membersById.put(id, member);
        return member;
    }

    public Member findMemberById(MemberId id) {
        return membersById.get(id);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(membersById.values());
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
