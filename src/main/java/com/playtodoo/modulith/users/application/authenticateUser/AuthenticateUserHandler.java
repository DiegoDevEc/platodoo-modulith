package com.playtodoo.modulith.users.application.authenticateUser;

import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.exception.TokenNotValidException;
import com.playtodoo.modulith.users.exception.UserNotFoundException;
import com.playtodoo.modulith.users.infrastructure.JwtService;
import com.playtodoo.modulith.users.infrastructure.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticateUserHandler{

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (Exception e) {
            throw new UserNotFoundException(request.email());
        }

        String[] parts = request.email().split("\\|");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Formato esperado: usuario|PLATAFORMA");
        }

        String username = parts[0];
        String platform = parts[1];

        User user = userRepository.findByEmailUsernamePhoneAndPlatform(username, platform)
                .orElseThrow(() -> new UsernameNotFoundException(username));


        String jwtToken = jwtService.generateToken(user);

        UserAuthResponse userAuthResponse = new UserAuthResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getUsername(),
                "",
                user.getStatus()
        );
        return new AuthenticateUserResponse(jwtToken, userAuthResponse);
    }

    public AuthenticateUserResponse authenticateFromAccessToken(AccessTokenRequest request) {
        String token = request.accessToken();
        String newToken;

        String userEmail;
        String platform;
        try {
            Claims claims = jwtService.extractAllClaims(token);
            userEmail = claims.get("email", String.class);
            platform = claims.get("platform", String.class);
        } catch (JwtException e) {
            log.error("Failed to extract username from JWT", e);
            throw new TokenNotValidException("Malformed JWT token");
        }

        if (userEmail == null) {
            log.debug("The JWT doesn't contain a username");
            throw new TokenNotValidException("Token does not contain a valid username");
        }
        String userData = userEmail + "|" + platform;

        User user = userRepository.findByEmailUsernamePhoneAndPlatform(userEmail, platform)
                .orElseThrow(() -> new UserNotFoundException(userData));

        boolean isTokenValid = jwtService.isTokenValid(token, user);
        boolean isTokenExpired = jwtService.isTokenExpired(token);
        boolean canBeRenewed = jwtService.canTokenBeRenewed(token);

        if (!isTokenValid || (isTokenExpired && !canBeRenewed)) {
            log.debug("The JWT is not valid");
            throw new TokenNotValidException("The JWT is not valid or cannot be renewed");
        }

        if (isTokenExpired) {
            log.debug("The JWT is expired and is going to be renewed");
            newToken = jwtService.renewToken(token, user);
        } else {
            newToken = token;
        }

        // Opcional: Actualizar el SecurityContext con el nuevo token
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAuthResponse userAuthResponse = new UserAuthResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getUsername(),
                "",  // Puedes incluir algún campo adicional si es necesario
                user.getStatus()
        );

        return new AuthenticateUserResponse(newToken, userAuthResponse);
    }

}
