package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.ActivityType;
import dk.ek.stud.group8.delfinen.domain.MembershipType;

final class ActivityTypeCodec {
    static String encode(MembershipType type) {
        return (switch (type) {
            case MembershipType.Active(var activity) -> switch (activity) {
                case ActivityType.Competitive _ -> ActivityTypeCell.COMPETITIVE;
                case ActivityType.Recreational _ -> ActivityTypeCell.RECREATIONAL;
            };
            case MembershipType.Passive _ -> ActivityTypeCell.NONE;
        }).name();
    }

    static ActivityTypeCell decode(String field) {
        return !field.isBlank()
                ? ActivityTypeCell.valueOf(field.trim())
                : ActivityTypeCell.NONE;
    }
}

enum ActivityTypeCell {
    NONE,
    RECREATIONAL,
    COMPETITIVE
}
