package com.switchfully.digibooky.security;
import com.switchfully.digibooky.api.dtos.mapper.MemberMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.switchfully.digibooky.domain.*;
import com.switchfully.digibooky.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;


import java.io.IOException;
import java.util.Base64;

@WebFilter("/*")
public class BasicAuthFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(BasicAuthFilter.class);
    private final MemberMapper memberMapper = new MemberMapper();
    private final MemberRepository memberRepository = new MemberRepository(memberMapper);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Skip authentication for "/digibooky/books" endpoints
        String uri = httpRequest.getRequestURI();
        if (uri.startsWith("/digibooky/books") || (uri.startsWith("/digibooky/members") && httpRequest.getMethod().equals("POST"))) {
            httpRequest.setAttribute("role","GUEST");
            chain.doFilter(request, response);  // Skip authentication, continue with the request
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring(6);  // Remove "Basic "
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":");

            String username = parts[0];
            String password = parts[1];

            log.info("Attempting to authenticate user with this request: " + request);

            // Validate username and password
            log.info("Attempting to authenticate user: " + memberRepository.getAllMembers());
            Member member = memberRepository.getMemberByUsername(username);
            log.info("User: " + member);

            if (member != null && member.getPassword().equals(password)) {
                // Authentication successful, set role in the request for the interceptor to use
                httpRequest.setAttribute("role", member.getRole().name());  // Set role as request attribute
                chain.doFilter(request, response);
            } else {
                // Authentication failure: wrong username/password
                httpResponse.setStatus(401);
                httpResponse.getWriter().write("Invalid username or password.");
                httpResponse.getWriter().flush();
            }
        } else {
            // No authorization header, or the format is invalid
            httpResponse.setStatus(401);
            httpResponse.getWriter().write("Authorization header missing or invalid.");
            httpResponse.getWriter().flush();
        }
    }
}
