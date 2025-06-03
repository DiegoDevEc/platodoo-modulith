package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserResponse;
import com.playtodoo.modulith.users.validation.SocialLogin;

public interface SocialAuthService {
    AuthenticateUserResponse generateAuthentificationBySocial(SocialLogin socialLogin);
}
