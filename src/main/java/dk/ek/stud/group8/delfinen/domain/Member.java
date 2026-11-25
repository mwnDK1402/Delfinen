package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class Member {
    private final LocalDate joinDate;
    private final MemberId memberId;
    private final String name;
    private final LocalDate birthDate;
    private MembershipType membershipType;

    public Member(
            LocalDate now,
            LocalDate joinDate,
            MemberId memberId,
            String name,
            LocalDate birthDate,
            MembershipType membershipType) {
        if (name.isBlank()) {
            throw new BlankNameException(name);
        }

        if (birthDate.isAfter(now)) {
            throw new FutureBirthDateException(now, birthDate);
        }

        if (membershipType instanceof MembershipType.Active(ActivityType type)
                && type instanceof ActivityType.Competitive(List<Discipline> disciplines)
                && disciplines.isEmpty()) {
            throw new IllegalArgumentException("Konkurrencesvømmer skal have mindst én disciplin");
        }

        this.joinDate = Objects.requireNonNull(joinDate);
        this.memberId = Objects.requireNonNull(memberId);
        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.membershipType = Objects.requireNonNull(membershipType);
    }

    public String getName() {
        return name;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public int getAgeInYears(LocalDate now) {
        return birthDate.until(Objects.requireNonNull(now)).getYears();
    }

    public boolean isPassive() {
        return membershipType instanceof MembershipType.Passive;
    }

    public Seniority getSeniority(LocalDate now) {
        return getAgeInYears(Objects.requireNonNull(now)) >= 18
                ? Seniority.Senior
                : Seniority.Junior;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = Objects.requireNonNull(membershipType);
    }

    // Should we move this into a Name class instead, wrapping the name attribute?
    public static final class BlankNameException extends RuntimeException {
        public BlankNameException(String name) {
            super("The given name was blank: " + name);
        }
    }

    public static final class FutureBirthDateException extends RuntimeException {
        public final LocalDate now;
        public final LocalDate birthDate;

        public FutureBirthDateException(LocalDate now, LocalDate birthDate) {
            super("The given birth date was in the future: " + birthDate);
            this.now = now;
            this.birthDate = birthDate;
        }
    }
}
