package pl.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user The user to be created
     * @return The created user
     */
    User createUser(User user);

    /**
     * Deletes a user by its id.
     *
     * @param userId id of the user to delete
     */
    void deleteUser(Long userId);

    /**
     * Find users whose email contains the given fragment (case-insensitive).
     *
     * @param fragment fragment to search inside email
     * @return list of matching users
     */
    java.util.List<User> findUsersByEmailFragment(String fragment);

    /**
     * Find users older than the provided date (birthdate before the given date).
     *
     * @param date exclusive upper bound for birthdate
     * @return list of users whose birthdate is before {@code date}
     */
    java.util.List<User> findUsersOlderThan(java.time.LocalDate date);

    /**
     * Updates an existing user with new data.
     *
     * @param userId id of the user to update
     * @param user user object with updated data
     * @return updated user
     */
    User updateUser(Long userId, User user);

}
