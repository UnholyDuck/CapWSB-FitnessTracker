package pl.wsb.fitnesstracker.statistics.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO reprezentujący raport treningowy dla użytkownika.
 * Zawiera podsumowanie treningów z danego okresu tygodniowego.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingReportDto {
    
    /** ID użytkownika */
    private Long userId;
    
    /** Imię użytkownika */
    private String firstName;
    
    /** Nazwisko użytkownika */
    private String lastName;
    
    /** Email użytkownika */
    private String email;
    
    /** Liczba treningów w ciągu tygodnia */
    private Integer trainingCount;
    
    /** Całkowita liczba treningów w systemie */
    private Integer totalTrainingCount;
    
    /** Łączny dystans przebiegniętych/przejechanych km w ciągu tygodnia */
    private Double totalDistance;
    
    /** Średnia prędkość ze wszystkich treningów w ciągu tygodnia */
    private Double averageSpeed;
    
    /** Tydzień (format: YYYY-WW) */
    private String weekPeriod;
}
