package com.switchfully.digibooky.api.dtos.mapper;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
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

    public Member DtoTo(CreateMemberDto createMemberDto) {
        return new Member(
                createMemberDto.getINSS(),
                createMemberDto.getLastName(),
                createMemberDto.getFirstName(),
                createMemberDto.getEmail(),
                createMemberDto.getStreetName(),
                createMemberDto.getStreetNumber(),
                createMemberDto.getCity(),
                createMemberDto.getRole()
        );
    }
}
