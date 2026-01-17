package pl.wsb.fitnesstracker.statistics.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.statistics.TrainingReportService;
import pl.wsb.fitnesstracker.statistics.api.TrainingReportDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Scheduler do automatycznego generowania raportów treningowych.
 * Uruchamia się co tydzień w ustalonym czasie (poniedziałek 07:00).
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TrainingReportScheduler {
    
    private final TrainingReportService trainingReportService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Scheduled task uruchamiany co 20 sekund (do testowania).
     * Cron expression: sekunda, minuta, godzina, dzień miesiąca, miesiąc, dzień tygodnia
     * 0/20 * * * * ? = co 20 sekund
     */
    @Scheduled(cron = "0/20 * * * * ?")
    public void generateWeeklyTrainingReports() {
        log.info("========================================");
        log.info("ROZPOCZĘCIE GENEROWANIA TYGODNIOWYCH RAPORTÓW TRENINGOWYCH");
        log.info("Czas: {}", LocalDateTime.now().format(FORMATTER));
        log.info("========================================");
        
        try {
            List<TrainingReportDto> reports = trainingReportService.generateWeeklyReports();
            
            log.info("\n");
            log.info("RAPORT TRENINGOWY - PODSUMOWANIE TYGODNIOWE");
            log.info("Liczba użytkowników: {}", reports.size());
            log.info("Tydzień: {}", reports.isEmpty() ? "N/A" : reports.get(0).getWeekPeriod());
            log.info("-------------------------------------------");
            
            // Wyświetlenie raportu dla każdego użytkownika
            reports.forEach(this::printReport);
            
            log.info("-------------------------------------------");
            log.info("KONIEC GENEROWANIA RAPORTÓW");
            log.info("Razem przetworzono {} użytkowników", reports.size());
            log.info("========================================\n");
            
        } catch (Exception e) {
            log.error("Błąd podczas generowania raportów treningowych", e);
        }
    }
    
    /**
     * Wyświetla raport dla pojedynczego użytkownika.
     */
    private void printReport(TrainingReportDto report) {
        log.info("USER: {} {} ({})", report.getFirstName(), report.getLastName(), report.getEmail());
        log.info("  └─ Treningi w bieżącym tygodniu: {}", report.getTrainingCount());
        log.info("  └─ Treningi razem: {}", report.getTotalTrainingCount());
        log.info("  └─ Całkowity dystans: {} km", String.format("%.2f", report.getTotalDistance()));
        log.info("  └─ Średnia prędkość: {} km/h", String.format("%.2f", report.getAverageSpeed()));
    }
}
