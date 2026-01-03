package com.uc_modul4.controller;

import com.uc_modul4.entity.Task;
import com.uc_modul4.repository.ITaskRepository;
import com.uc_modul4.service.IProjectService;
import com.uc_modul4.service.ITaskService;
import com.uc_modul4.service.IUserService; // Sử dụng Interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired private ITaskService taskService;
    @Autowired private IProjectService projectService;
    @Autowired private ITaskRepository taskRepository;
    @Autowired private IUserService userService; // Đã đổi khớp với Service của bạn

    /**
     * Hiển thị bảng Kanban
     * Đây là phương thức quan trọng nhất để sửa lỗi 404
     */
    @GetMapping("/kanban")
    public String showKanban(Model model) {
        // 1. Lấy dữ liệu từ Service
        List<Task> tasks = taskService.findAll();

        // 2. Đưa dữ liệu vào Model để Thymeleaf sử dụng
        model.addAttribute("tasks", tasks);
        model.addAttribute("statuses", Task.TaskStatus.values()); // TODO, IN_PROGRESS, DONE
        model.addAttribute("users", userService.findAll());

        // 3. Cấu hình layout (Fragment)
        model.addAttribute("content", "tasks/kanban :: content");
        model.addAttribute("activeMenu", "kanban");

        return "layout/main-layout";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam(required = false) Long projectId) {
        // Đảm bảo projectId không null để tránh lỗi Business Logic
        Long pId = (projectId != null) ? projectId : 1L;

        task.setStatus(Task.TaskStatus.TODO);
        taskService.createTask(task, pId);
        return "redirect:/tasks/kanban";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks/kanban";
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("content", "tasks/calendar :: content");
        model.addAttribute("activeMenu", "calendar");
        return "layout/main-layout";
    }
}