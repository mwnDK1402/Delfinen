package dk.ek.stud.group8.delfinen.persistence;

import dk.ek.stud.group8.delfinen.domain.Member;
import dk.ek.stud.group8.delfinen.domain.Member.BlankNameException;
import dk.ek.stud.group8.delfinen.domain.MemberId;
import dk.ek.stud.group8.delfinen.domain.MembershipType;
import dk.ek.stud.group8.delfinen.persistence.MembershipTypeCodec.NoActivityTypeException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

final class MemberCodec {
    private static final List<String> HEADER_COLUMNS = List.of(
            "memberId",
            "name",
            "birthDate",    // YYYY-MM-DD
            "active",       // true/false
            "activityType", // null/Recreational/Competitive
            "disciplines",  // null/BUTTERFLY,CRAWL,BACKCRAWL,BREASTSTROKE
            // TODO: Add training results (potentially in another TSV file?)
            "joinDate"      // YYYY-MM-DD
    );

    static String header() {
        return String.join(DsvConfig.COLUMN_DELIMITER, HEADER_COLUMNS);
    }

    static String encode(Member member) {
        var memberCells = MembershipTypeCodec.encode(member.getMembershipType());
        return String.join(DsvConfig.COLUMN_DELIMITER,
                String.valueOf(member.getMemberId().value()),
                member.getName(),
                DateCodec.encode(member.getBirthDate()),
                memberCells, // This already contains delimiters
                DateCodec.encode(member.getJoinDate())
        );
    }

    /**
     * @return null on failure
     */
    static Member decode(LocalDate now, String row) {
        String[] cells = row.split(DsvConfig.COLUMN_DELIMITER, -1);
        if (cells.length != HEADER_COLUMNS.size()) {
            return null;
        }
        try {
            int memberId = Integer.parseInt(cells[0]);
            String name = cells[1];
            LocalDate birthdayDate = DateCodec.decode(cells[2]);
            MembershipType membershipType = MembershipTypeCodec.decode(cells[3], cells[4], cells[5]);
            LocalDate joinDate = DateCodec.decode(cells[6]);

            return new Member(
                    now,
                    new MemberId(memberId),
                    name,
                    birthdayDate,
                    membershipType,
                    joinDate);

        } catch (
                NumberFormatException |
                BlankNameException |
                DateTimeParseException |
                NoActivityTypeException |
                MembershipTypeCodec.NoDisciplineException _) {
            return null;
        }
    }
}
