package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;

public final class Member {
    private final String name;
    private final LocalDate birthDate;

    public Member(String name, LocalDate birthDate) {
        final LocalDate now = LocalDate.now();
        if (birthDate.isAfter(now))
            throw new FutureBirthDateException(now, birthDate);

        this.name = name;
        this.birthDate = birthDate;
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
