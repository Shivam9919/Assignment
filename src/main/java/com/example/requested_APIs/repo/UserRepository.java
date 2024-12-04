package com.example.requested_APIs.repo;

import com.example.requested_APIs.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user exists with a given username.
     *
     * @param username the username to check
     * @return true if a user exists with the given username, otherwise false
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user exists with a given email.
     *
     * @param email the email to check
     * @return true if a user exists with the given email, otherwise false
     */
    boolean existsByEmail(String email);
}
