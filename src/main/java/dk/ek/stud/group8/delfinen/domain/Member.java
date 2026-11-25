package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public final class Member {
    private final String name;
    private final LocalDate birthDate;
    private final int memberId;
    private MembershipType membershipType;
    private ActivityType activityType;
    private ArrayList<Discipline> disciplines;
    private final LocalDate joinDate;

    /*public Member(String name, LocalDate birthDate, MemberId memberId, ActivityType activityType, LocalDate now) {
        if (name.isBlank())
            throw new BlankNameException(name);

        // TODO: Parameterize 'now' for the sake of the test suite
        if (birthDate.isAfter(now))
            throw new FutureBirthDateException(now, birthDate);

        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.memberId = Objects.requireNonNull(memberId);
        this.activityType = activityType;
    }*/

    public Member(int memberId, String name, LocalDate birthDate, MembershipType membershipType,
                  ActivityType activityType, ArrayList<Discipline> disciplines, LocalDate joinDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Navn må ikke være tomt");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fødselsdatoen kan ikke ligge i fremtiden");
        }
        if (membershipType == MembershipType.PASSIVE && activityType != null) {
            throw new IllegalArgumentException("Passive medlemmer har ingen aktivitetsform");
        }
        if (membershipType == MembershipType.ACTIVE && activityType == null) {
            throw new IllegalArgumentException("Aktive medlemmer skal have en aktivitetsform");
        }
        if (activityType == ActivityType.Competitive && disciplines.isEmpty()) {
            throw new IllegalArgumentException("Konkurrencesvømmer skal have mindst én disciplin");
        }
        if (activityType != ActivityType.Competitive && !disciplines.isEmpty()) {
            throw new IllegalArgumentException("Kun konkurrencesvømmere kan have discipliner");
        }

        this.name = name;
        this.birthDate = birthDate;
        this.membershipType = membershipType;
        this.memberId = memberId;
        this.activityType = activityType;
        this.disciplines = disciplines;
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getAgeInYears(LocalDate now) {
        return birthDate.until(now).getYears();
    }

    public boolean isPassive() {
        return activityType == null;
    }

    public Seniority getSeniority(LocalDate now) {
        return getAgeInYears(now) >= 18
                ? Seniority.Senior
                : Seniority.Junior;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
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
