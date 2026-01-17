package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.UserDtoLite;

import java.util.Date;

/**
 * Data Transfer Object for Training entity.
 * Used to transfer training information between API layers.
 * Contains all training details including nested user information.
 */
public class TrainingDto {

    private Long id;
    private UserDtoLite user;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00")
    private Date endTime;

    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

    /**
     * Default constructor for TrainingDto.
     */
    public TrainingDto() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id training id
     * @param user user details (UserDtoLite)
     * @param startTime training start time
     * @param endTime training end time
     * @param activityType type of activity
     * @param distance training distance
     * @param averageSpeed average speed during training
     */
    public TrainingDto(Long id, UserDtoLite user, Date startTime, Date endTime,
                       ActivityType activityType, double distance, double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDtoLite getUser() {
        return user;
    }

    public void setUser(UserDtoLite user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return "TrainingDto{" +
                "id=" + id +
                ", user=" + user +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityType=" + activityType +
                ", distance=" + distance +
                ", averageSpeed=" + averageSpeed +
                '}';
    }
}
