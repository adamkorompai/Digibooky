package com.switchfully.digibooky.security;

import com.switchfully.digibooky.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleBasedAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Retrieve the user's role from the request (set in BasicAuthFilter)
        String roleString = (String) request.getAttribute("role");
        if("GUEST".equals(roleString)) {
            return true;
        }

        if (roleString != null) {
            Role userRole = Role.valueOf(roleString);  // Convert string to Role enum

            // Get the requested URI
            String uri = request.getRequestURI();

            // Apply role-based access control (you can extend this logic)
            if (uri.startsWith("/digibooky/members") && userRole != Role.MEMBER) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Forbidden
                response.getWriter().write("You need MEMBER role to access this resource.");
                return false;  // Don't continue to the controller
            } else if (uri.startsWith("/digibooky/admin") && userRole != Role.ADMIN) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You need ADMIN role to access this resource.");
                return false;
            } else if (uri.startsWith("/digibooky/librarian") && userRole != Role.LIBRARIAN) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You need LIBRARIAN role to access this resource.");
                return false;
            }

            // No restrictions for BookController, request can continue
            return true;

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Unauthorized
            response.getWriter().write("Authorization is required.");
            return false;  // Don't continue to the controller
        }
    }
}
