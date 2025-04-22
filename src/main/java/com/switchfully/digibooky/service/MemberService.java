package com.switchfully.digibooky.service;

import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository repository;
    private final MemberMapper mapper;

    @Autowired
    public MemberService(MemberRepository repository, MemberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CreateMemberDto saveMember(CreateMemberDto createMemberDto) {

        validateMemberFormat(createMemberDto);
        System.out.println(createMemberDto);
        Member member = mapper.DtoTo(createMemberDto);
        System.out.println(member);
        repository.save(member);

        repository.getAllMembers().forEach((member1) -> {log.info(member1.toString());});
        return createMemberDto;

    }

    public MemberDto createAdmin(MemberDto memberDto) {
        validateAdminLibrarianFormat(memberDto);
        return repository.createAdmin(memberDto);

    }

    public MemberDto createLibrarian(MemberDto memberDto) {
        validateAdminLibrarianFormat(memberDto);
        return repository.createLibrarian(memberDto);
    }

    public List<MemberDto> getAllMember() {
        List<Member> members = repository.getAllMembers();
        return members.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public void validateMemberFormat(CreateMemberDto createMemberDto) {
        validateNoNullEmptyFields(createMemberDto.getEmail(), "Email is required");
        validateEmailFormat(createMemberDto.getEmail());
        validateNoNullEmptyFields(createMemberDto.getLastName(), "Lat name  is required");
        validateNoNullEmptyFields(createMemberDto.getCity(), "City is required");
        validateNoNullEmptyFields(createMemberDto.getUsername(), "Username is required");
        validateNoNullEmptyFields(createMemberDto.getPassword(), "Password is required");

        if(repository.getAllInsses().contains(createMemberDto.getInss())) {
            throw new IllegalArgumentException("The given INSS is already in use");
        }
        if(repository.getAllEmails().contains(createMemberDto.getEmail())) {
            throw new IllegalArgumentException("The given email is already in use");
        }
        if(repository.getAllUsernames().contains(createMemberDto.getUsername())) {
            throw new IllegalArgumentException("The given username is already in use");
        }

    }

    private void validateAdminLibrarianFormat(MemberDto memberDto) {
        validateNoNullEmptyFields(memberDto.getEmail(), "Email is required");
        validateEmailFormat(memberDto.getEmail());
        validateNoNullEmptyFields(memberDto.getLastName(), "Last name is required");
        validateNoNullEmptyFields(memberDto.getUsername(), "Username is required");
        validateNoNullEmptyFields(memberDto.getPassword(), "Password is required");
        if(repository.getAllUsernames().contains(memberDto.getUsername())) {
            throw new IllegalArgumentException("The given username is already in use");
        }
    }

    private void validateEmailFormat(String email) {
        if (email != null && !email.isEmpty()) {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // format x@x.x where x is any number of letter/number
            Pattern pattern = Pattern.compile(emailRegex);
            if (!pattern.matcher(email).matches()) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
    }

    private void validateNoNullEmptyFields(String Field, String errorMessage) {
        if (Field == null || Field.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }





}
