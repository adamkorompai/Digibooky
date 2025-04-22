package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MemberRepositoryTest {


    private MemberRepository memberRepository;

    //TODO: to remove because normally no mapper in repository

    private MemberMapper memberMapper;

    @BeforeEach
    public void setUp() {
        memberMapper = new MemberMapper();
        memberRepository = new MemberRepository(memberMapper);
    }

    /*

    Member admin = Member.createAdmin(
                "Bob",
                "Bobby",
                "bob_bobby@hotmail.com"
        );
        Member member1 = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com");
        Member member2 = Member.createLibrarian("Gandalf","leblanc","gadalf_leblan@hotmail.com");
        Member member3 = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);

     */

    @Test
    public void whenRepositoryCreated_AtLeastOneAdmin(){
        List<Member> members = memberRepository.getAllMembers().stream().filter(member -> member.getRole().equals(Role.ADMIN)).toList();
        assertFalse(members.isEmpty());
    }

    @Test
    public void whenCreatingAdmin_AdminIsAdded(){
        int size = memberRepository.getAllMembers().stream().filter(member -> member.getRole().equals(Role.ADMIN)).toList().size();
        memberRepository.createAdmin(new MemberDto("admin", "admin", "admin@admin.admin", "admin", "admin"));
        int new_size = memberRepository.getAllMembers().stream().filter(member -> member.getRole().equals(Role.ADMIN)).toList().size();
        assertEquals(size + 1, new_size);
    }

    @Test
    public void whenCreatingLibrarian_LibrarianIsAdded(){
        int size = memberRepository.getAllMembers().stream().filter(member -> member.getRole().equals(Role.LIBRARIAN)).toList().size();
        memberRepository.createLibrarian(new MemberDto("Librarian", "Librarian", "Librarian@Librarian.Librarian","librarian", "librarian"));
        int new_size = memberRepository.getAllMembers().stream().filter(member -> member.getRole().equals(Role.LIBRARIAN)).toList().size();
        assertEquals(size + 1, new_size);
    }




}
