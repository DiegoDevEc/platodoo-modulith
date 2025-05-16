package com.playtodoo.modulith.users.infrastructure.filter;

import com.playtodoo.modulith.users.infrastructure.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtAuthFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            jwt = authHeader.substring(7);

            userEmail = jwtService.extractUsername(jwt);

            if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                log.debug("The JWT doesn't contains a username");
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            boolean isTokenValid = jwtService.isTokenValid(jwt, userDetails);
            boolean isTokenExpired = jwtService.isTokenExpired(jwt);
            boolean canBeRenewed = jwtService.canTokenBeRenewed(jwt);

            if (!isTokenValid || (isTokenExpired && !canBeRenewed)) {
                log.debug("The JWT is not valid");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            if (isTokenExpired) {
                log.debug("The JWT is expired and is going to be renewed");
                String newToken = jwtService.renewToken(jwt, userDetails);
                response.setHeader("Authorization", "Bearer " + newToken);
            }

            Claims claims = jwtService.extractAllClaims(jwt);

            List<String> roles = claims.get("roles", List.class);

            Set<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)  // Respeta prefijo "ROLE_"
                    .collect(Collectors.toSet());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            log.error("Error processing JWT: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

        filterChain.doFilter(request, response);

    }
}
