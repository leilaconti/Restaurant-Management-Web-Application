package team14.arms.backend.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import team14.arms.backend.data.entity.User;
import team14.arms.backend.repositories.UserRepository;

/**
 * Implements the {@link UserDetailsService}.
 *
 * This implementation searches for {@link User} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the {@link User} from the database using the e-mail address
     * given in the login screen.
     *
     * @param username User's e-mail address
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username);
        if (null == user) {
            throw new UsernameNotFoundException("No user found with email: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        }
    }
}
