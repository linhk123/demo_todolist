package com.uc_modul4.service.Service;

import com.uc_modul4.entity.Task;
import com.uc_modul4.repository.ITaskRepository;
import com.uc_modul4.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {
    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Map<String, Long> getTaskStatistics(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().collect(Collectors.groupingBy(
                t -> t.getStatus().name(), Collectors.counting()
        ));
    }

    @Override
    public Map<LocalDate, Integer> getProductivityReport(Long userId, LocalDate start, LocalDate end) {
        // Thống kê số lượng task DONE của User theo từng ngày
        List<Task> doneTasks = taskRepository.findAllByAssignedToIdAndStatusAndDueDateBetween(
                userId, Task.TaskStatus.DONE, start, end);

        return doneTasks.stream().collect(Collectors.groupingBy(
                Task::getDueDate, Collectors.summingInt(t -> 1)
        ));
    }
}
