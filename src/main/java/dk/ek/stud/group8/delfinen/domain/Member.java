package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public final class Member {
    private final String name;
    private final LocalDate birthDate;
    private final MemberId memberId;

    public Member(String name, LocalDate birthDate, MemberId memberId) {
        if(name.isBlank())
            throw new BlankNameException(name);

        final LocalDate now = LocalDate.now();
        if (birthDate.isAfter(now))
            throw new FutureBirthDateException(now, birthDate);

        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.memberId = Objects.requireNonNull(memberId);
    }

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
