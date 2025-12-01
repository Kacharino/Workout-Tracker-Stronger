package at.kacharino.workouttrackerstronger.security;

import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(optionalUser.get().getEmail())
                    .password(optionalUser.get().getPassword())
                    .authorities("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + email);
        }
    }
}