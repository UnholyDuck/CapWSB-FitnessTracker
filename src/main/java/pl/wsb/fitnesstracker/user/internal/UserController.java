package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.format.annotation.DateTimeFormat;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.User;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


        /**
     * GET /v1/users
     * Returns a list of all users.
     *
     * @return list of {@link UserDto} representing all users
     */
        @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * GET /v1/users/simple
     * Returns a list of all users with basic information (id, firstName, lastName).
     *
     * @return list of {@link UserSimpleDto} with basic user information
     */
    @GetMapping("/simple")
    public List<pl.wsb.fitnesstracker.user.api.UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * GET /v1/users/{id}
     * Retrieve a user by its identifier.
     *
     * @param id user id path variable
     * @return {@link UserDto} of the requested user
     * @throws IllegalArgumentException when user with given id does not exist
     */
         @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }


        /**
     * GET /v1/users/email/{email}
     * Retrieve a user by their email address.
     *
     * @param email user email path variable
     * @return {@link UserDto} of the requested user
     * @throws IllegalArgumentException when no user with the given email exists
     */
        @GetMapping("/email/{email}")
public UserDto getUserByEmail(@PathVariable String email) {
    return userService.getUserByEmail(email)
            .map(userMapper::toDto)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
}

    /**
     * GET /v1/users/email?email=fragment
     * Search users by email fragment (case-insensitive) and return only id and email.
     *
     * @param email fragment to search for in email addresses
     * @return list of {@link pl.wsb.fitnesstracker.user.api.UserEmailDto}
     */
    @GetMapping(value = "/email", params = "email")
    public java.util.List<pl.wsb.fitnesstracker.user.api.UserEmailDto> searchUsersByEmail(@RequestParam("email") final String email) {
        return userService.findUsersByEmailFragment(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /**
     * POST /v1/users
     * Create a new user from provided {@link UserDto} payload.
     * Only public fields from the payload are used; any provided id is ignored.
     *
     * @param userDto request body with user data
     * @return created {@link UserDto} containing generated id
     */
    @PostMapping
    public UserDto createUser(@RequestBody final UserDto userDto) {
        final User domainUser = new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );

        final User created = userService.createUser(domainUser);
        return userMapper.toDto(created);
    }

    /**
     * DELETE /v1/users/{userId}
     * Delete the user identified by the given id.
     * Returns HTTP 204 No Content on success.
     *
     * @param userId id of the user to delete
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * GET /v1/users/older/{time}
     * Return users older than the given date (birthdate before {@code time}).
     * Date must be in ISO format (yyyy-MM-dd).
     *
     * @param time date used as exclusive upper bound for birthdate
     * @return list of {@link UserDto} for users older than the provided date
     */
    @GetMapping("/older/{time}")
    public java.util.List<UserDto> getUsersOlderThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final java.time.LocalDate time) {
        return userService.findUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }










    


    /**
     * PUT /v1/users/{userId}
     * Update an existing user with the provided data.
     *
     * @param userId id of the user to update
     * @param userDto request body with updated user data
     * @return updated {@link UserDto}
     */
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody final UserDto userDto) {
        final User domainUser = new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );

        final User updated = userService.updateUser(userId, domainUser);
        return userMapper.toDto(updated);
    }

}
