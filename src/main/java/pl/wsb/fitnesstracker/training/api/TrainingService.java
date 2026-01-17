package pl.wsb.fitnesstracker.training.api;

import java.util.List;

/**
 * Service interface for managing Training operations.
 * Provides methods for retrieving training data.
 */
public interface TrainingService {

    /**
     * Retrieve all trainings from the database.
     *
     * @return list of all trainings
     */
    List<Training> getAllTrainings();

    /**
     * Retrieve all trainings for a specific user.
     *
     * @param userId id of the user
     * @return list of trainings for the given user
     */
    List<Training> getTrainingsByUserId(Long userId);

}
