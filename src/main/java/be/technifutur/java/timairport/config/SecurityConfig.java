package be.technifutur.java.timairport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    /**
     * <h2>RequestMatchers</h2>
     * (are evaluated in order, so the most specific matchers should be first)
     *    Method
     *    - a value of HttpMethod
     *
     *
     *    Pattern: 1 ou plsr  chaine de caractères représentant des URI
     *    - ?  : matches a single character
     *    - *  : matches zero or more characters
     *    - ** : matches zero or more directories in a path
     *
     * Authorization:
     *    - permitAll()                : allow all
     *    - denyAll()                  : deny all
     *    - anonymous()                : allow only anonymous
     *    - authenticated()            : allow only authenticated
     *    - hasAuthority(String)       : allow only users with the specified authority
     *    - hasAnyAuthority(String...) : allow users with the specified authorities
     *    - hasRole(String)            : allow only users with the specified role
     *    - hasAnyRole(String...)      : allow users with the specified roles
     *    -
     *
     *    Une authorité est une chaîne de caractères qui représente une permission.
     *    auth : 'ROLE_TRUC' <-> role : 'TRUC'
     *
     *    Authorité et rôle définissent la même chose dans le programme. Par convention, l'authorité est utilisée pour décrire les actions et le rôle pour décrire les utilisateurs
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests((authorize) -> {
            authorize
                    //Via RequestMatchers
                    .requestMatchers(request -> request.getMethod().equals(HttpMethod.GET)).hasRole("ADMIN")
                    // Via HttpMethod
                    .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                    //Via Mapping d'URI
                    .requestMatchers("plane/all").anonymous()
                    .requestMatchers("/plane/add").authenticated()
                    .requestMatchers("plane/{id:[0-9]+}").hasRole("ADMIN")
                    .requestMatchers("/plane/*/update").hasRole("ADMIN")//.hasAuthority("ROLE_ADMIN")
                    //Via HttpMethod + maping d'URI
                    .requestMatchers(HttpMethod.DELETE, "/plane/*").hasAnyRole("ADMIN", "USER")//.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .requestMatchers((request) -> request.getMethod().equals(HttpMethod.GET) ||
                            request.getMethod().equals(HttpMethod.POST) ||
                            request.getMethod().equals(HttpMethod.DELETE) ||
                            request.getMethod().equals(HttpMethod.PATCH) &&
                            request.getRequestURI().equals("/flight/**")).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/flight/**").hasRole("ADMIN")
                    .requestMatchers("/flight/**").authenticated()
                    .anyRequest().permitAll();
        });

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailService(PasswordEncoder encoder) {

        List<UserDetails> users = List.of(
                User.builder()
                        .username("user")
                        .password(encoder.encode("pass"))
                        .roles("USER")
                        .build(),
                User.builder()
                        .username("admin")
                        .password(encoder.encode("pass"))
                        .roles("ADMIN", "USER")
                        .build()
                );

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
