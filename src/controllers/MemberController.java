package controllers;
import models.Member;

public class MemberController {
    //private List<Member> members;
    private Member member;

    public MemberController() {
    }
    public MemberController(Member member) {
        this.member = member;
    }

    public void displayMemberInfo() {
        this.member.displayMemberInfo();
    }

//    public static void main(String[] args) {
//        Member member = new Member( 1,"name", "address", "phone", "email", "membershipDate", "password");
//        MemberController memberController = new MemberController(member);
//        memberController.displayMemberInfo();
//    }


}