package com.switchfully.digibooky.api.dtos;

import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Role;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.RoundEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberMapperTest {

    private final MemberMapper memberMapper = new MemberMapper();

    @Test
    public void mapToMemberDto_shouldConvertToMemberDtoCorrectly() {
        Member member = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);
        MemberDto result = memberMapper.toDto(member);

        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo("Lisa");
        assertThat(result.getFirstName()).isEqualTo("Simpson");
        assertThat(result.getStreetName()).isEqualTo("Evergreen Terrace");
        assertThat(result.getStreetNumber()).isEqualTo("742");
        assertThat(result.getCity()).isEqualTo("Springfield");
        assertThat(result.getRole()).isEqualTo(Role.MEMBER);
    }

    @Test
    public void mapDtoToMember_shouldConvertDtoToMemberCorrectly() {
        CreateMemberDto dto = new CreateMemberDto("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER);
        Member result = memberMapper.DtoTo(dto);

        assertThat(result).isNotNull();
        assertThat(result.getINSS()).isEqualTo("951014-523-12");
        assertThat(result.getLastName()).isEqualTo("Lisa");
        assertThat(result.getFirstName()).isEqualTo("Simpson");
        assertThat(result.getStreetName()).isEqualTo("Evergreen Terrace");
        assertThat(result.getStreetNumber()).isEqualTo("742");
        assertThat(result.getCity()).isEqualTo("Springfield");
        assertThat(result.getRole()).isEqualTo(Role.MEMBER);
    }
}
