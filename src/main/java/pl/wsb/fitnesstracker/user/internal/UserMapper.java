package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;

@Component
/**
 * Mapper for converting between domain `User` and API `UserDto` objects.
 *
 * This class centralizes field mapping so controllers and services can work
 * with immutable DTOs when exposing user data.
 */
class UserMapper {
    
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Map domain `User` to lightweight `UserEmailDto` containing only id and email.
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(), user.getEmail());
    }

}
