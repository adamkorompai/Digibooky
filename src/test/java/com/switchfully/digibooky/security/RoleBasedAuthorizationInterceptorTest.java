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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleBasedAuthorizationInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberRepository memberRepository;

    @Test
    public void shouldAllowAccessForAdmin() throws Exception {
        Member admin = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com","peter1","passwordpeter");
        when(memberRepository.getMemberByUsername("peter1")).thenReturn(admin);

        mockMvc.perform(get("/digibooky/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("peter1:passwordpeter")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn403ForMemberAccessingAdmin() throws Exception {
        Member validMember = new Member("951014-523-12","Lisa","Simpson","lisa_simpson@hotmail.com","Evergreen Terrace","742","Springfield", Role.MEMBER,"lisa1","passwordlisa");
        when(memberRepository.getMemberByUsername("lisa1")).thenReturn(validMember);

        mockMvc.perform(get("/digibooky/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("lisa1:passwordlisa")))
                .andExpect(status().isForbidden())
                .andExpect(content().string("You need ADMIN role to access this resource."));

    }

    @Test
    public void shouldAllowCreationNewBookForLibrarian() throws Exception {
        // Mock librarian user
        Member librarian = Member.createLibrarian("Gandalf","leblanc","gadalf_leblan@hotmail.com","gadalf1","passwordgadalf");
        when(memberRepository.getMemberByUsername("gadalf1")).thenReturn(librarian);

        String jsonContent = "{"
                + "\"isbn\": \"978-92-95055-02-5\","
                + "\"title\": \"The frustrating Project\","
                + "\"author\": {"
                + "\"firstname\": \"Jane\","
                + "\"lastname\": \"Doe\""
                + "},"
                + "\"summary\": \"A thrilling adventure story.\","
                + "\"numberOfCopy\": 2"
                + "}";

        mockMvc.perform(post("/digibooky/librarian")  // POST method
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("gadalf1:passwordgadalf"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn403ForAdminTryingToCreateNewBook() throws Exception {
        Member admin = Member.createAdmin("Peter", "Pan", "peter_pan@hotmail.com","peter1","passwordpeter");
        when(memberRepository.getMemberByUsername("peter1")).thenReturn(admin);

        String jsonContent = "{"
                + "\"isbn\": \"978-92-95055-02-5\","
                + "\"title\": \"The frustrating Project\","
                + "\"author\": {"
                + "\"firstname\": \"Jane\","
                + "\"lastname\": \"Doe\""
                + "},"
                + "\"summary\": \"A thrilling adventure story.\","
                + "\"numberOfCopy\": 2"
                + "}";

        mockMvc.perform(post("/digibooky/librarian")  // POST method
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBase64("peter1:passwordpeter"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(content().string("You need LIBRARIAN role to access this resource."));
    }

    private String encodeBase64(String credentials) {
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
