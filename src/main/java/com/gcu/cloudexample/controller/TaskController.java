package com.gcu.cloudexample.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gcu.cloudexample.model.Task;
import com.gcu.cloudexample.model.User;
import com.gcu.cloudexample.services.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String listTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("tasks", taskService.getTasksForUser(user));
        return "task-list";
    }

    @GetMapping("/new")
    public String showNewTaskForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/new")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        task.setUser(user.getId());
        taskService.createTask(task);
        return "redirect:/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Task task = taskService.getTaskById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUser().equals(user.getId())) {
            return "redirect:/tasks";
        }
        
        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        taskService.updateTask(task);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        taskService.deleteTask(id, user.getId());
        return "redirect:/dashboard";
    }
}