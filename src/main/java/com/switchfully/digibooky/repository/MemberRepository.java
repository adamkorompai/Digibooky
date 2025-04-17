package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    private final ConcurrentHashMap<String, Member> members;

    public MemberRepository() {
        members = new ConcurrentHashMap<>();
        addAdmin();


    }

    private void addAdmin() {
        Member admin = Member.createAdmin(
                "121018-352-22",
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






}
