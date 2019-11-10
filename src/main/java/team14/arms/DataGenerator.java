package team14.arms;

import com.vaadin.flow.spring.annotation.SpringComponent;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import team14.arms.backend.data.entity.User;
import team14.arms.backend.data.model.Role;
import team14.arms.backend.repositories.UserRepository;

@SpringComponent
public class DataGenerator implements HasLogger {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {

        if (userRepository.count() != 0L) {
            getLogger().info("Using existing database");
            return;
        }

        getLogger().info("Initialising database with demo data ...");

        getLogger().info("... generating users");
        User admin = userRepository.save(createUser("admin@email.com", "John", "Doe", passwordEncoder.encode("admin"), Role.ADMIN, true));
        User chef = userRepository.save(createUser("chef@email.com", "James", "Doe", passwordEncoder.encode("chef"), Role.CHEF, true));
        User waiter = userRepository.save(createUser("waiter@email.com", "Jack", "Doe", passwordEncoder.encode("waiter"), Role.WAITER, true));

        getLogger().info("Initialised database with demo data");
    }

    private User createUser(String email, String firstName, String lastName, String passwordHash, String role, boolean locked) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        user.setLocked(locked);
        return user;
    }

}
