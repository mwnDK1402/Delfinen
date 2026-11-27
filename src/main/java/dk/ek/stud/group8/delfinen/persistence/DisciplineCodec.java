package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.Discipline;

import java.util.HashSet;
import java.util.Set;

final class DisciplineCodec {
    static String encode(Set<Discipline> disciplines) {
        // TODO(#37): Rewrite this without ninja tricks
        return String.join(DsvConfig.ELEMENT_DELIMITER,
                disciplines
                        .stream()
                        .map(Enum::name)
                        .toList());
    }

    static Set<Discipline> decode(String cell) {
        HashSet<Discipline> result = new HashSet<>(4);
        String[] disciplines = cell.split(DsvConfig.ELEMENT_DELIMITER, -1);
        for (String discipline : disciplines) {
            if (discipline.isBlank()) continue;
            // TODO: Load training results by discipline
            result.add(Discipline.valueOf(discipline));
        }

        return result;
    }
}
