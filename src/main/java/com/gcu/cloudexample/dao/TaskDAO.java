package com.gcu.cloudexample.dao;

import com.gcu.cloudexample.model.Task;
import com.gcu.cloudexample.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    List<Task> findByUser(User user);
    Optional<Task> findById(Long id);
    void save(Task task);
    void update(Task task);
    void delete(Long taskId, Long userId);
}
