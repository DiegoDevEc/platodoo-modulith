package com.playtodoo.modulith.admin;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    /**
     * Retorna información resumida del sistema para el dashboard administrativo
     */
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        // Estos serían datos reales que obtendrías de tus repositorios
        summary.put("totalUsers", 125);
        summary.put("totalSportComplexes", 15);
        summary.put("totalReservations", 2456);
        summary.put("activeUsers", 98);
        summary.put("pendingReservations", 42);
        
        // Estadísticas de uso
        Map<String, Integer> usageStats = new HashMap<>();
        usageStats.put("tennis", 850);
        usageStats.put("soccer", 1200);
        usageStats.put("basketball", 406);
        summary.put("usageStats", usageStats);
        
        // Ingresos mensuales (ejemplo)
        Map<String, Double> monthlyRevenue = new HashMap<>();
        monthlyRevenue.put("enero", 12500.0);
        monthlyRevenue.put("febrero", 13200.0);
        monthlyRevenue.put("marzo", 14100.0);
        monthlyRevenue.put("abril", 15000.0);
        monthlyRevenue.put("mayo", 16200.0);
        summary.put("monthlyRevenue", monthlyRevenue);
        
        return summary;
    }
}
