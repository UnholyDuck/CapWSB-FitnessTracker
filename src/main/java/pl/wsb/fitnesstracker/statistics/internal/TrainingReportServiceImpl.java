package pl.wsb.fitnesstracker.statistics.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.statistics.TrainingReportService;
import pl.wsb.fitnesstracker.statistics.api.TrainingReportDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Implementacja serwisu do generowania raportów treningowych.
 * Oblicza statystyki treningowe dla użytkowników na podstawie danych z ostatniego tygodnia.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingReportServiceImpl implements TrainingReportService {
    
    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    
    @Override
    public List<TrainingReportDto> generateWeeklyReports() {
        log.info("Generowanie tygodniowych raportów treningowych dla wszystkich użytkowników");
        
        List<User> allUsers = userProvider.findAllUsers();
        
        return allUsers.stream()
                .map(this::generateUserWeeklyReport)
                .toList();
    }
    
    @Override
    public TrainingReportDto generateUserWeeklyReport(Long userId) {
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik nie znaleziony: " + userId));
        
        return generateUserWeeklyReport(user);
    }
    
    /**
     * Generuje raport dla użytkownika na podstawie jego treningu z ostatniego tygodnia.
     */
    private TrainingReportDto generateUserWeeklyReport(User user) {
        log.debug("Generowanie raportu tygodniowego dla użytkownika: {}", user.getEmail());
        
        // Pobranie granic czasowych dla bieżącego tygodnia
        Date weekStart = getWeekStart();
        Date weekEnd = getWeekEnd();
        
        // Pobranie wszystkich treningów
        List<Training> allTrainings = trainingRepository.findAll();
        
        // Filtrowanie treningów dla danego użytkownika w bieżącym tygodniu
        List<Training> weeklyTrainings = allTrainings.stream()
                .filter(training -> training.getUser() != null && training.getUser().getId().equals(user.getId()))
                .filter(training -> isWithinWeek(training.getStartTime(), weekStart, weekEnd))
                .toList();
        
        // Wyliczenie statystyk
        int trainingCount = weeklyTrainings.size();
        double totalDistance = weeklyTrainings.stream()
                .mapToDouble(Training::getDistance)
                .sum();
        double averageSpeed = trainingCount > 0
                ? weeklyTrainings.stream()
                        .mapToDouble(Training::getAverageSpeed)
                        .average()
                        .orElse(0.0)
                : 0.0;
        
        // Całkowita liczba treningów użytkownika
        int totalTrainingCount = (int) allTrainings.stream()
                .filter(training -> training.getUser() != null && training.getUser().getId().equals(user.getId()))
                .count();
        
        // Tworzenie raportu
        TrainingReportDto report = new TrainingReportDto();
        report.setUserId(user.getId());
        report.setFirstName(user.getFirstName());
        report.setLastName(user.getLastName());
        report.setEmail(user.getEmail());
        report.setTrainingCount(trainingCount);
        report.setTotalDistance(totalDistance);
        report.setAverageSpeed(averageSpeed);
        report.setTotalTrainingCount(totalTrainingCount);
        report.setWeekPeriod(getCurrentWeekPeriod());
        
        log.debug("Raport wygenerowany - treningi: {}, dystans: {}, średnia prędkość: {}",
                trainingCount, totalDistance, averageSpeed);
        
        return report;
    }
    
    /**
     * Sprawdza, czy data treningu falls within bieżącego tygodnia.
     */
    private boolean isWithinWeek(Date trainingDate, Date weekStart, Date weekEnd) {
        return trainingDate != null && 
               trainingDate.after(weekStart) && 
               trainingDate.before(weekEnd);
    }
    
    /**
     * Pobiera początek bieżącego tygodnia (poniedziałek 00:00).
     */
    private Date getWeekStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * Pobiera koniec bieżącego tygodnia (niedziela 23:59).
     */
    private Date getWeekEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    /**
     * Zwraca bieżący tydzień w formacie YYYY-WW.
     */
    private String getCurrentWeekPeriod() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        return String.format("%04d-W%02d", year, weekOfYear);
    }
}
