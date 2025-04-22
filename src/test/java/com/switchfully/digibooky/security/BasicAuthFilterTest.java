package com.switchfully.digibooky.security;

import com.switchfully.digibooky.domain.Member;
import com.switchfully.digibooky.domain.Role;
import com.switchfully.digibooky.repository.MemberRepository;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class BasicAuthFilterTest {

   @Autowired
    private MockMvc mockMvc;

   @MockitoBean
    private MemberRepository memberRepository;


    @Test
    public void shouldAllowRequestWithoutAuthorizationForPublicEndpoints() throws Exception {
        mockMvc.perform(get("/digibooky/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn401ForMissingAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/digibooky/members"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Authorization header missing or invalid."));
    }

    @Test
    public void shouldReturn401ForInvalidCredentials() throws Exception {
        // Prepare a mock invalid authorization header (wrong username/password)
        mockMvc.perform(get("/digibooky/members")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("wronguser:wrongpassword")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }

    @Test
    public void shouldAllowAuthenticatedRequestForMemberWithValidCredentials() throws Exception {
        // Setup mock behavior for successful authentication
        Member validMember = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER,"lisa1","passwordlisa");
        when(memberRepository.getMemberByUsername("lisa1"))
                .thenReturn(validMember);

        // Prepare a JSON payload with all necessary member information
        String jsonContent = "{"
                + "\"username\":\"lisa1\","
                + "\"password\":\"passwordlisa\","
                + "\"email\":\"lisa_simpson@hotmail.com\","
                + "\"firstName\":\"Lisa\","
                + "\"lastName\":\"Simpson\","
                + "\"address\":\"742 Evergreen Terrace\","
                + "\"city\":\"Springfield\","
                + "\"role\":\"MEMBER\""
                + "}";

        // Perform the POST request to create a member
        mockMvc.perform(post("/digibooky/members")  // POST method
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("lisa1:passwordlisa"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn403ForMemberAccessingAdminEndpoint() throws Exception {
        // Setup mock behavior for valid member trying to access an admin-only endpoint
        Member validMember = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER,"lisa1","passwordlisa");
        when(memberRepository.getMemberByUsername("lisa1")).thenReturn(validMember);

        mockMvc.perform(get("/digibooky/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("lisa1:passwordlisa")))
                .andExpect(status().isForbidden()) // Should return 403 for unauthorized access
                .andExpect(content().string("You need ADMIN role to access this resource."));
    }

    private String encodeBase64(String credentials) {
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }


}
