package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Implementation of {@link UserService} and {@link UserProvider} backed by a JPA repository.
 *
 * This class performs basic CRUD operations and simple search utilities for {@link User} entities.
 */
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user in the database.
     * The provided user must not have an id already set — this method enforces creation-only semantics.
     *
     * @param user domain user to create
     * @return persisted user with generated id
     * @throws IllegalArgumentException when {@code user.getId()} is not null
     */
    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Delete a user by its id. Delegates to {@link UserRepository#deleteById(Object)}.
     *
     * @param userId id of the user to delete
     */
    @Override
    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Find users whose email contains the provided fragment (case-insensitive).
     *
     * @param fragment partial email to search for
     * @return list of matching users; empty list when {@code fragment} is null or no matches found
     */
    @Override
    public java.util.List<User> findUsersByEmailFragment(final String fragment) {
        return userRepository.findByEmailContainingIgnoreCase(fragment == null ? "" : fragment);
    }

    /**
     * Find users older than the provided date. A user is considered "older" when their birthdate is before
     * the supplied {@code date}.
     *
     * @param date exclusive upper bound for birthdate; when {@code null} an empty list is returned
     * @return list of users with birthdate before {@code date}
     */
    @Override
    public java.util.List<User> findUsersOlderThan(final java.time.LocalDate date) {
        if (date == null) {
            return java.util.Collections.emptyList();
        }
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getBirthdate() != null && u.getBirthdate().isBefore(date))
                .toList();
    }

    /**
     * Retrieve a user by id.
     *
     * @param userId id of the user to retrieve
     * @return Optional containing the user when found, or empty when not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieve a user by exact email match.
     *
     * @param email email to search for
     * @return Optional with found user or empty when none matched
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Return all users from the repository.
     *
     * @return list of all users
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}