package dk.ek.stud.group8.delfinen;

public record Ticket(int number) {
    private static int nextNumber = 1;

    public Ticket {
        if (number != nextNumber - 1)
            throw new IllegalArgumentException("Use the constructor without arguments");
    }

    public Ticket() {
        this(nextNumber++);
    }
}
