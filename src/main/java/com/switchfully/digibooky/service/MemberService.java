package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberService {

    private final MemberRepository repository;
    private final MemberMapper mapper;

    public MemberService(MemberRepository repository, MemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MemberDto saveMember(String inss,MemberDto memberDto) {

        verification(inss,memberDto);
        Member member = mapper.DtoTo(inss,memberDto);
        repository.save(member);
        return memberDto;

    }

    public void verification(String inss,MemberDto memberDto) {
        if (memberDto.getEmail() == null || memberDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (memberDto.getLastName() == null || memberDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if(repository.getAllInsses().contains(inss)) {
            throw new IllegalArgumentException("The given inss is already in use");
        }
        if(memberDto.getCity() == null || memberDto.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }



    }




}
