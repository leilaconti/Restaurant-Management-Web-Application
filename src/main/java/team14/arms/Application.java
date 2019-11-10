package team14.arms;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import team14.arms.backend.data.entity.User;
import team14.arms.backend.repositories.UserRepository;
import team14.arms.backend.security.SecurityConfiguration;
import team14.arms.backend.service.UserService;
import team14.arms.ui.MainView;

/**
 * Spring Boot Web App initialiser.
 */
@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, MainView.class, Application.class, UserService.class }, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@EntityScan(basePackageClasses = { User.class })
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


}
