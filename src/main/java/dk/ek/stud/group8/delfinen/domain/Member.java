package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class Member {
    private final String name;
    private final LocalDate birthDate;
    private final MemberId memberId;
    private ActivityType activityType;

    public Member(LocalDate now, String name, LocalDate birthDate, MemberId memberId, ActivityType activityType) {
        if (name.isBlank())
            throw new BlankNameException(name);

        // TODO: Parameterize 'now' for the sake of the test suite
        if (birthDate.isAfter(now))
            throw new FutureBirthDateException(now, birthDate);

        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.memberId = Objects.requireNonNull(memberId);
        this.activityType = activityType;
    }

    public MemberId getMemberId() {
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
