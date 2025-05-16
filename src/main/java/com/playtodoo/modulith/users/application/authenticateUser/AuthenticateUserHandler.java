package com.playtodoo.modulith.users.application.authenticateUser;

import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.exception.UserNotFoundException;
import com.playtodoo.modulith.users.infrastructure.JwtService;
import com.playtodoo.modulith.users.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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

        User user = userRepository.findByEmailUsernamePhone(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));


        String jwtToken = jwtService.generateToken(user);

        return new AuthenticateUserResponse(jwtToken);
    }
}
