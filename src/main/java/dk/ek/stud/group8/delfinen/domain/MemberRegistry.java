package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.*;

public final class MemberRegistry {
    private final Random rng;
    private final Map<MemberId, Member> membersById;

    public MemberRegistry(Random rng) {
        this.rng = rng;
        this.membersById = new HashMap<>();
        // TODO: Load members from CSV file
    }

    /**
     * Print a number of member ids padded to 4 digits.
     */
    static void main() {
        MemberRegistry memberRegistry = new MemberRegistry(new Random());

        for (int i = 0; i < 100; i++) {
            Member member = memberRegistry.register(
                    LocalDate.now(),
                    "Tobias", LocalDate.of(2000, 05, 05),
                    MembershipType.PASSIVE,
                    null,
                    List.of());
            System.out.printf("%04d%n", member.getMemberId().value());
        }
    }

    public Member register(
            LocalDate joinDate,
            String name,
            LocalDate birthDate,
            MembershipType membershipType,
            ActivityType activityType,
            List<Discipline> disciplines) {
        var id = nextId();
        Member member = new Member(id, name, birthDate, membershipType, activityType, disciplines, joinDate);
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
