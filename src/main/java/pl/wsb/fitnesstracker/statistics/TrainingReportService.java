package pl.wsb.fitnesstracker.statistics;

import pl.wsb.fitnesstracker.statistics.api.TrainingReportDto;

import java.util.List;

/**
 * Interfejs serwisu do generowania raportów treningowych.
 * Zapewnia funkcjonalność do tworzenia podsumowań treningów dla użytkowników.
 */
public interface TrainingReportService {
    
    /**
     * Generuje raport treningowy dla wszystkich użytkowników za bieżący tydzień.
     * 
     * @return Lista raportów treningowych dla każdego użytkownika
     */
    List<TrainingReportDto> generateWeeklyReports();
    
    /**
     * Generuje raport treningowy dla konkretnego użytkownika za bieżący tydzień.
     * 
     * @param userId ID użytkownika
     * @return Raport treningowy dla danego użytkownika
     */
    TrainingReportDto generateUserWeeklyReport(Long userId);
}
