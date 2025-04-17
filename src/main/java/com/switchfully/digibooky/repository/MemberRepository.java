package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
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
    private final ConcurrentHashMap<String, Member> members;
    private final MemberMapper memberMapper;

    public MemberRepository(MemberMapper memberMapper) {
        members = new ConcurrentHashMap<>();
        //addAdmin();
        Member admin00 = Member.createAdmin(
                "Bob",
                "Bobby",
                "bob_bobby@hotmail.com"
        );
        log.info("Creating admin: {}", admin00);
        members.put(admin00.getId(),admin00);
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
