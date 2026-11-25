package dk.ek.stud.group8.delfinen.domain;

public enum Discipline {
    BUTTERFLY("Butterfly"),
    CRAWL("Crawl"),
    BACKCRAWL("Rygcrawl"),
    BREASTSTROKE("Brystsvømning");

    private final String displayName;

    Discipline(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
