package dk.ek.stud.group8.delfinen.domain;

import java.util.List;

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
    record Competitive(List<Discipline> disciplines) implements ActivityType {}
}
