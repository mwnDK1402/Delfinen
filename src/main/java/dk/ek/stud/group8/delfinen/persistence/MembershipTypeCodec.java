package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.ActivityType;
import dk.ek.stud.group8.delfinen.domain.Discipline;
import dk.ek.stud.group8.delfinen.domain.MembershipType;
import dk.ek.stud.group8.delfinen.domain.TrainingResult;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

final class MembershipTypeCodec {
    static String encode(MembershipType type) {
        String isActive = String.valueOf(type instanceof MembershipType.Active);
        String activityType = ActivityTypeCodec.encode(type);
        String disciplines = DisciplineCodec.encode(switch (type) {
            case MembershipType.Active active -> switch (active.type()) {
                case ActivityType.Competitive competitive -> competitive.bestByDiscipline().keySet();
                case ActivityType.Recreational _ -> Set.of();
            };
            case MembershipType.Passive _ -> Set.of();
        });

        return String.join(DsvConfig.COLUMN_DELIMITER,
                isActive,
                activityType,
                disciplines);
    }

    /**
     * @throws NoActivityTypeException when membership is active but activity type is none
     */
    static MembershipType decode(String isActiveCell, String activityTypeCell, String disciplinesCell) {
        boolean isActive = Boolean.parseBoolean(isActiveCell);
        // Hvis medlemmerne er passive, har de ingen aktivitetsform
        ActivityTypeCell activityType = ActivityTypeCodec.decode(activityTypeCell);
        Set<Discipline> disciplines = DisciplineCodec.decode(disciplinesCell);
        Map<Discipline, TrainingResult> bestByDiscipline = disciplines.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        _ -> TrainingResult.NONE));

        if (!isActive) {
            return new MembershipType.Passive();
        }

        return switch (activityType) {
            case NONE -> throw new NoActivityTypeException();
            case RECREATIONAL -> new MembershipType.Active(new ActivityType.Recreational());
            case COMPETITIVE -> {
                if (bestByDiscipline.isEmpty()) {
                    throw new NoDisciplineException();
                }
                yield new MembershipType.Active(new ActivityType.Competitive(bestByDiscipline));
            }
        };
    }

    static final class NoActivityTypeException extends RuntimeException {
        public NoActivityTypeException() {
            super("An active member must have an activity type");
        }
    }

    static final class NoDisciplineException extends RuntimeException {
        public NoDisciplineException() {
            super("A competitive member must have at least one discipline");
        }
    }
}
