package com.gcu.cloudexample.services;

import com.gcu.cloudexample.dao.TaskDAO;
import com.gcu.cloudexample.model.Task;
import com.gcu.cloudexample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskDAO;

    public List<Task> getTasksForUser(User user) {
        return taskDAO.findByUser(user);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskDAO.findById(id);
    }

    public void createTask(Task task) {
        taskDAO.save(task);
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    
    public void deleteTask(Long taskId, Long userId) {
        taskDAO.delete(taskId, userId);
    }
}