package com.uc_modul4.service;

import java.time.LocalDate;
import java.util.Map;

public interface IReportService {
    Map<String, Long> getTaskStatistics(Long projectId);

    // Báo cáo hiệu suất (Số task hoàn thành theo thời gian)
    Map<LocalDate, Integer> getProductivityReport(Long userId, LocalDate start, LocalDate end);
}
