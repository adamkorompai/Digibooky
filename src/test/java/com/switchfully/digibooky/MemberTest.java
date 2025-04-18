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
        this.memberMapper = new MemberMapper();
        this.memberRepository = new MemberRepository(memberMapper);

        this.memberService = new MemberService(memberRepository,memberMapper);
    }

    // -------------------------------------------- Create New Member ------------------------------


    /**
     * This test is made to check if when a User is created, the isbn is not used
     * So the validation is made inside the service and this test call the service to
     * see if the check is made
     *
     * */
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


    /**
     * This test is made to check if when a User is created, the email is not already
     * used So the validation is made inside the service and
     * this test call the service to see if the check is made
     *
     * */

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

    /**
     *
     * Test to see if a member is created, the member role is automatically attributed
     *
     * */

    @Test
    public void whenCreatingAMember_ThenMemberHasAMemberRole() {
        CreateMemberDto dtoCreated = new CreateMemberDto(
                "741223-558-34",
                "Jack",
                "Jack_Sparrow@hotmail.com",
                "Brussels"
        );
        memberService.saveMember(dtoCreated);
        Member member = getLastMemberAdded();
        Assertions.assertEquals(member.getRole(), Role.MEMBER);
    }


    /**
     * If the email is null then throws an Exception
     *
     * */


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

    /**
     * If the lastname is null then throws an Exception
     *
     * */

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

    /**
     * If the city is null then throws an Exception
     *
     * */


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

    /**
     *  A method to get the last member added, it has to be changed because
     *  it seem that the order of the map change so I cant really get the last data
     *  added inside the map
     *
     * */

    private Member getLastMemberAdded() {
        List<Member> members = memberRepository.getAllMembers();
        System.out.println("getLastMemberAdded method : "+ members);
        return members.get(0);

    }

    //TODO Test : Empty Email
    //TODO Test : Empty City
    //TODO Test : Empty lastname
    //TODO Test : Empty INSS
    //TODO Test : Bad Email Format(xxx@xxx.xxx)
    //TODO Test : Bad City Format(Only letters)
    //TODO Test : Bad lastname format(Only Letters)
    //TODO Test :
    //TODO Test :
    //TODO Test :





}
