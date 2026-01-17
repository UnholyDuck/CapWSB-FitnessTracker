package pl.wsb.fitnesstracker.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.statistics.api.TrainingReportDto;

import java.util.List;

/**
 * REST controller do obsługi raportów treningowych.
 * Dostarcza endpoints do ręcznego generowania raportów (na potrzeby testowania).
 */
@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class TrainingReportController {
    
    private final TrainingReportService trainingReportService;
    
    /**
     * Pobiera raporty treningowe dla wszystkich użytkowników za bieżący tydzień.
     * 
     * @return Lista raportów treningowych
     */
    @GetMapping("/trainings/weekly")
    public List<TrainingReportDto> getWeeklyReports() {
        log.info("Żądanie: GET /v1/reports/trainings/weekly - pobierz wszystkie raporty tygodniowe");
        return trainingReportService.generateWeeklyReports();
    }
    
    /**
     * Pobiera raport treningowy dla konkretnego użytkownika za bieżący tydzień.
     * 
     * @param userId ID użytkownika
     * @return Raport treningowy dla danego użytkownika
     */
    @GetMapping("/trainings/weekly/{userId}")
    public TrainingReportDto getUserWeeklyReport(@PathVariable Long userId) {
        log.info("Żądanie: GET /v1/reports/trainings/weekly/{} - pobierz raport dla użytkownika", userId);
        return trainingReportService.generateUserWeeklyReport(userId);
    }
}
