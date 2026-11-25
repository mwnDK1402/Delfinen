package dk.ek.stud.group8.delfinen.domain;

public sealed interface MembershipType {
    record Passive() implements MembershipType {}
    record Active(ActivityType type) implements MembershipType {}
}
