package com.nutikas.backendnutikas.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equalsIgnoreCase("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        var authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                var token = authorizationHeader.substring("Bearer ".length());
                // TODO: Make it secure!
                var algorithm = Algorithm.HMAC256("secret".getBytes());
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(token);
                var username = decodedJWT.getSubject();
                var roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                var responseMap = new HashMap<String, String>();
                responseMap.put("error", e.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
