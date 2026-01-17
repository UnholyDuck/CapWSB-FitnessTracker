package pl.wsb.fitnesstracker.training.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TrainingService} and {@link TrainingProvider} backed by a JPA repository.
 * Provides CRUD operations and queries for Training entities.
 */
@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Constructor for TrainingServiceImpl.
     *
     * @param trainingRepository JPA repository for Training entities
     */
    public TrainingServiceImpl(final TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Retrieve all trainings from the database.
     *
     * @return list of all trainings
     */
    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Retrieve all trainings for a specific user.
     * Filters trainings by user ID using streams.
     *
     * @param userId id of the user
     * @return list of trainings for the given user
     */
    @Override
    public List<Training> getTrainingsByUserId(final Long userId) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getUser() != null && training.getUser().getId().equals(userId))
                .toList();
    }

    /**
     * Retrieve a training by its id.
     *
     * @param trainingId id of the training to retrieve
     * @return Optional containing the training when found, or empty when not found
     */
    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

}

