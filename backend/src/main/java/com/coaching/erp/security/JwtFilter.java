package com.coaching.erp.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();
        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
    chain.doFilter(request, response);
    return;
}
        //  Allow login API
        if (path.contains("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) response).sendError(401, "No Token");
            return;
        }

        String token = header.substring(7);

        try {
            String username = JwtUtil.extractUsername(token);
            String role = JwtUtil.extractRole(token);

            // 🔥 THIS IS THE MAIN FIX
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            ((HttpServletResponse) response).sendError(401, "Invalid Token");
            return;
        }

        chain.doFilter(request, response);
    }
}