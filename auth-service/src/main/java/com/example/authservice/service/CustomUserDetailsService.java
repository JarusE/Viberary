package com.example.authservice.service;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.UserResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * An instance of {@link UserServiceClient} used to communicate with the User Service
     * for operations such as retrieving user details based on an email.
     *
     * This client facilitates interaction with the User Service by abstracting
     * HTTP requests and responses, enabling seamless user-related operations
     * within this service.
     *
     * Used primarily in {@link CustomUserDetailsService} for fetching user
     * information during authentication processes.
     */
    private final UserServiceClient client;

    /**
     * Constructs an instance of the {@code CustomUserDetailsService}.
     *
     * @param client the {@code UserServiceClient} used to communicate with the user service
     */
    public CustomUserDetailsService(UserServiceClient client) {
        this.client = client;
    }

    /**
     * Loads a user based on the provided email address.
     *
     * This method retrieves user details such as email, password, and role
     * from an external user service. If the user is not found, it throws a
     * UsernameNotFoundException. The loaded user details are encapsulated into
     * a Spring Security {@link User} object and returned.
     *
     * @param email the email address of the user to retrieve
     * @return a {@link UserDetails} object containing the user's information
     * @throws UsernameNotFoundException if no user is found for the provided email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponse user = client.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        System.out.println("User received: " + user.email());
        System.out.println("Password hash: " + user.password());
        System.out.println("Role: " + user.role());

        return new User(
                user.email(),
                user.password(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.role().toUpperCase()))
        );
    }

}
