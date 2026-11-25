package dk.ek.stud.group8.delfinen.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class MemberRegister {

    // private final Random random = new Random();
    private ArrayList<Member> members;
    private int nextMemberId;

    /*public MemberRegister(ArrayList<Member> members) {
        this.members = members;
        this.nextMemberId = findNextId();
    }*/

    public MemberRegister() {
        members = new ArrayList<>();
    }

    public void addMember(String name, LocalDate birthDate, MembershipType membershipType,
                          ActivityType activityType, ArrayList<Discipline> disciplines, LocalDate now) {

        int memberId = nextMemberId;
        nextMemberId++;

        Member member = new Member(memberId, name, birthDate, membershipType, activityType, disciplines, now);
        members.add(member);
    }

    public Member findMember(int memberId) {
        for (Member m : members) {
            if (m.getMemberId() == memberId) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    private int findNextId() {
        int highest = 0;
        for (Member m : members) {
            if(m.getMemberId() > highest) {
                highest = m.getMemberId();
            }
        }
        return highest + 1;
    }

    /*private int generateUniqueId() {
        int id;
        do {
            id = 1000 + random.nextInt(9000);
        } while (findMember(id) != null);
        return id;
    }*/
}

