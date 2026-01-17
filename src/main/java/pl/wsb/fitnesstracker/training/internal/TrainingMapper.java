package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDtoLite;

/**
 * Mapper for converting between domain Training entity and API TrainingDto.
 * Handles mapping of User to UserDtoLite for nested user information.
 */
@Component
public class TrainingMapper {

    /**
     * Convert Training entity to TrainingDto.
     * Includes nested user information as UserDtoLite.
     *
     * @param training domain Training entity
     * @return TrainingDto with all training and user information
     */
    public TrainingDto toDto(Training training) {
        if (training == null) {
            return null;
        }

        UserDtoLite userDto = toUserDtoLite(training.getUser());

        return new TrainingDto(
                training.getId(),
                userDto,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    /**
     * Convert User entity to lightweight UserDtoLite.
     *
     * @param user domain User entity
     * @return UserDtoLite with basic user information
     */
    private UserDtoLite toUserDtoLite(User user) {
        if (user == null) {
            return null;
        }

        return new UserDtoLite(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

}
