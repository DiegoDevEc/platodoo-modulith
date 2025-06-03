package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserResponse;
import com.playtodoo.modulith.users.application.authenticateUser.UserAuthResponse;
import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.exception.TechnicalErrorException;
import com.playtodoo.modulith.users.infrastructure.JwtService;
import com.playtodoo.modulith.users.infrastructure.UserRepository;
import com.playtodoo.modulith.users.mapper.UserMapper;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.SocialLogin;
import com.playtodoo.modulith.users.validation.SocialUserInfo;
import com.playtodoo.modulith.users.validation.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SocialAuthServiceImpl implements SocialAuthService {

    private final String GOOGLE_VERITY_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper mapper;
    private final UserRepository userRepository;

    @Override
    public AuthenticateUserResponse generateAuthentificationBySocial(SocialLogin socialLogin) {

        SocialUserInfo socialUserInfo = verifyGoogleToken(socialLogin.token());

        User user = userRepository.findByEmailUsernamePhoneAndPlatform(socialLogin.email(), socialLogin.platform())
                .orElseGet(() -> {
                    Set<String> roles = rolesFromPlatform(socialLogin.platform());

                    CreateUserDTO createUserDTO = new CreateUserDTO(
                            getUsernameFromEmail(socialUserInfo.email()),
                            socialLogin.firstName(),
                            socialLogin.lastName(),
                            "ACT",
                            socialLogin.email(),
                            "",
                            socialLogin.platform(),
                            socialLogin.provider(),
                            "",
                            roles,
                            true
                    );

                    UserDto userDto = userService.createUser(createUserDTO);
                    return mapper.toUser(userDto);
                });

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



    private SocialUserInfo verifyGoogleToken(String idToken) {

        ResponseEntity<Map> response = new RestTemplate().getForEntity(GOOGLE_VERITY_URL + idToken, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new TechnicalErrorException("error.authentication.google");
        }

        Map<String, Object> body = response.getBody();

        return new SocialUserInfo(
                (String) body.get("email"),
                (String) body.get("name"),
                (String) body.get("picture")
        );
    }

    private String getUsernameFromEmail(String email){
        return email.split("@")[0];
    }

    private Set<String> rolesFromPlatform(String platform){
        if(platform.equals("MANAGER")){
            return Set.of("ROLE_MANAGER");
        }
        if(platform.equals("APP_MOBIL")){
            return Set.of("ROLE_USER");
        }
        return Set.of("ROLE_SUPER_ADMIN");
    }


}
