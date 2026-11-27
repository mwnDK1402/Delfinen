package dk.ek.stud.group8.delfinen.domain;

public record MemberId(int value) {
    public MemberId {
        if (value < 1) {
            throw new IllegalArgumentException("Member id must be positive (was " + value + ")");
        }
    }
}
