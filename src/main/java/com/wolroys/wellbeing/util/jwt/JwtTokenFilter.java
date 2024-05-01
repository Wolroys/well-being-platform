package com.wolroys.wellbeing.util.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(req);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                if (authentication != null)
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(req, res);
        } catch (ExpiredJwtException | MalformedJwtException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setHeader("Content-Type", "application/json");
            res.getWriter().write("{\n\t\"violation\":{\n\t\t\"error\":\"JWT token expired or invalid\"\n\t}\n}");
            res.flushBuffer();

        }
    }
}
