package dk.ek.stud.group8.delfinen.domain;

import java.util.Set;

/**
 * (Danish: <i>Aktivitetsform</i>)
 */
public sealed interface ActivityType {
    /**
     * (Danish: <i>Motionist</i>)
     */
    record Recreational() implements ActivityType {}
    /**
     * (Danish: <i>Konkurrencesvømmer</i>)
     */
    record Competitive(Set<Discipline> disciplines) implements ActivityType {}
}
