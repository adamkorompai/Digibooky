package com.switchfully.digibooky;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Role;
import com.switchfully.digibooky.repository.MemberRepository;
import com.switchfully.digibooky.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberTest {

    private static final Logger log = LoggerFactory.getLogger(MemberTest.class);
    private MemberRepository memberRepository;
    private MemberService memberService;
    private MemberMapper memberMapper;

    private Member member = new Member(
            "741223-558-34",
            "Jack",
            "Sparrow",
            "Brussels",
            Role.MEMBER
    );

    @BeforeEach
    void setUp() {
        this.memberRepository = new MemberRepository();
        this.memberMapper = new MemberMapper();

        this.memberService = new MemberService(memberRepository,memberMapper);
    }

    @Test
    public void whenSavingMemberToDb_GivingInssThatAlreadyExists_ThenThrowException() {
        Member member = memberRepository.getAllMembers().get(0);
        CreateMemberDto createMemberDto = new CreateMemberDto(
                member.getINSS(),
                "Jack",
                "mehdi_sellam@hotmail.com",
                "Brussels"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(createMemberDto);
        });
    }

    @Test
    public void whenSavingMemberToDb_GivingEmailThatAlreadyExists_ThenThrowException() {
        Member member = memberRepository.getAllMembers().get(0);
        CreateMemberDto createMemberDto = new CreateMemberDto(
                "741223-558-34",
                "Jack",
                member.getEmail(),
                "Brussels"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(createMemberDto);
        });
    }

    @Test
    public void whenCreatingAMember_ThenMemberHasAMemberRole() {
        CreateMemberDto dtoCreated = new CreateMemberDto(
                "741223-558-34",
                "Jack",
                "Jack_Sparrow",
                "Brussels"
        );
        memberService.saveMember(dtoCreated);
        Member member = getLastMemberAdded();
        Assertions.assertEquals(member.getRole(), Role.MEMBER);
    }

    @Test
    public void whenCreatingAMember_WithNoEmail_ThenThrowException() {
        CreateMemberDto dtoCreated = new CreateMemberDto(
                "741223-558-34",
                "Jack",
                null,
                "Brussels"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(dtoCreated);
        });
    }

    @Test
    public void whenCreatingAMember_WithNoLastName_ThenThrowException() {
        CreateMemberDto dtoCreated = new CreateMemberDto(
                "741223-558-34",
                null,
                "Jack_Sparrow@Hotmail.com",
                "Brussels"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(dtoCreated);
        });
    }

    @Test
    public void whenCreatingAMember_WithNoCity_ThenThrowException() {
        CreateMemberDto dtoCreated = new CreateMemberDto(
                "741223-558-34",
                "Jack",
                "Jack_Sparrow@Hotmail.com",
                null
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(dtoCreated);
        });
    }

    private Member getLastMemberAdded(){
        List<Member> members = memberRepository.getAllMembers();
        return members.get(members.size()-1);

    }



}
