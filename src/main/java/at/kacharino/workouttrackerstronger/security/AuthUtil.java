package at.kacharino.workouttrackerstronger.security;

import at.kacharino.workouttrackerstronger.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuthUtil {

    private final UserService userService;

    public AuthUtil(UserService userService) {
        this.userService = userService;
    }

    public Long getAuthenticatedUserId(){
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByEmail(email);
        return user.getId();
    }




}
