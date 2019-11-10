package team14.arms.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import team14.arms.backend.data.entity.User;
import team14.arms.backend.data.model.Role;
import team14.arms.backend.repositories.UserRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/";

    private final UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * A password encoder (using bcrypt) for encrypting passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CurrentUser currentUser(UserRepository userRepository) {
        final String username = SecurityUtils.getUsername();
        User user = username != null ? userRepository.findByEmailIgnoreCase(username) : null;
        return () -> user;
    }

    /**
     * Configures `UserDetailsService` with the password encoder to be used
     * for login attempts.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Not using Spring CSRF here to be able to use plain HTML for the login page.
        http.csrf().disable()

            // Save unauthorized access attempts, so the user is redirected
            // after login.
            .requestCache().requestCache(new CustomRequestCache())

            // Allow all users to access the /menu page
            .and().authorizeRequests().antMatchers("/menu*").permitAll()

            // Restrict access to our application.
            .and().authorizeRequests()

            // Allow all flow internal requests.
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

            // Allow all requests by logged in users.
            .anyRequest().hasAnyAuthority(Role.getAllRoles())

            // Configure the login page.
            .and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
            .failureUrl(LOGIN_FAILURE_URL)

            // Register the success handler that redirects users to the last page
            // they tried to access.
            .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())

            // Configure logout.
            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    /**
     * IMPORTANT: Only to be used to temporarily disable the login authentication during development.
     */
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
    }
    */

    /**
     * Allow access to static resources (bypasses Spring security).
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest", "/sw.js", "/offline-page.html",
            "/icons/**", "/images/**",
            "/frontend/**",
            "/webjars/**",
            "/h2-console/**",
            "/frontend-es5/**", "/frontend-es6/**"
        );
    }
}
