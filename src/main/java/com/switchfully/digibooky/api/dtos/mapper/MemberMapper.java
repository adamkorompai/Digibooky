package com.switchfully.digibooky.api.dtos.mapper;

import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getLastName(),
                member.getFirstName(),
                member.getEmail(),
                member.getStreetName(),
                member.getStreetNumber(),
                member.getCity(),
                member.getRole()
        );
    }

    public Member DtoTo(String inss,MemberDto memberdto) {
        return new Member(
                inss,
                memberdto.getLastName(),
                memberdto.getFirstName(),
                memberdto.getEmail(),
                memberdto.getStreetName(),
                memberdto.getStreetNumber(),
                memberdto.getCity(),
                memberdto.getRole()
        );
    }
}
