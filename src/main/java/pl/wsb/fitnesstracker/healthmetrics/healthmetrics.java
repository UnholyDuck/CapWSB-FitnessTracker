package pl.wsb.fitnesstracker.healthmetrics;


import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import pl.wsb.fitnesstracker.user.api.User;

@Entity
@Table(name = "Health_Metrics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "user")
public class Healthmetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int weight;
    
    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int heartRate;

    public Healthmetrics(LocalDate date, double weight, double height, int heartRate) {
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.heartRate = heartRate;
    }
}