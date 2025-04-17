package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository repository;
    private final MemberMapper mapper;

    public MemberService(MemberRepository repository, MemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CreateMemberDto saveMember(CreateMemberDto createMemberDto) {

        verification(createMemberDto);
        Member member = mapper.DtoTo(createMemberDto);
        repository.save(member);

        repository.getAllMembers().forEach((member1) -> {log.info(member1.toString());});
        return createMemberDto;

    }

    public void verification(CreateMemberDto createMemberDto) {
        if (createMemberDto.getEmail() == null || createMemberDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (createMemberDto.getLastName() == null || createMemberDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if(repository.getAllInsses().contains(createMemberDto.getInss())) {
            throw new IllegalArgumentException("The given inss is already in use");
        }
        if(createMemberDto.getCity() == null || createMemberDto.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }



    }




}
