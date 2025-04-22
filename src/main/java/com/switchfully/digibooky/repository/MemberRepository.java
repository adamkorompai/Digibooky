package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    private static final Logger log = LoggerFactory.getLogger(MemberRepository.class);
    private final HashMap<String, Member> members;
    private final MemberMapper memberMapper;

    public MemberRepository(MemberMapper memberMapper) {
        members = new HashMap<>();
        addAdmin();
        initializeData();
        this.memberMapper = memberMapper;
    }

    private void addAdmin() {
        Member admin = Member.createAdmin(
                "Bob",
                "Bobby",
                "bob_bobby@hotmail.com"
        );
        members.put(admin.getId(), admin);
    }
    public void initializeData() {
        Member member1 = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com");
        Member member2 = Member.createLibrarian("Gandalf","leblanc","gadalf_leblan@hotmail.com");
        Member member3 = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);

        members.put(member1.getId(), member1);
        members.put(member2.getId(), member2);
        members.put(member3.getId(), member3);

    }

    public Member save(Member member) {
        return members.put(member.getId(), member);
    }

    public List<Member> getAllMembers() {

        return new ArrayList<>(members.values());
    }

    public List<String> getAllInsses() {
        return members.values().stream().map(Member::getINSS).collect(Collectors.toList());
    }

    public List<String> getAllEmails() {
        return members.values().stream().map(Member::getEmail).collect(Collectors.toList());
    }
    public Member getMember(String id) {
        return members.get(id);
    }

    public MemberDto createAdmin(MemberDto memberDto) {
        Member admin = Member.createAdmin(memberDto.getLastName(), memberDto.getFirstName(), memberDto.getEmail());
        members.put(admin.getId(), admin);
        return memberMapper.toDto(admin);
    }

    public MemberDto createLibrarian(MemberDto memberDto) {
        Member librarian = Member.createLibrarian(memberDto.getLastName(), memberDto.getFirstName(), memberDto.getEmail());
        members.put(librarian.getId(), librarian);
        return memberMapper.toDto(librarian);
    }





}
