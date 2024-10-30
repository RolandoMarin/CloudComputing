package com.gcu.cloudexample.dao;

import com.gcu.cloudexample.model.Task;
import com.gcu.cloudexample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public TaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Task> findByUser(User user) {
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        List<Task> tasks = new ArrayList<>();

        try {
            SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, user.getId());
            while (srs.next()) {
                tasks.add(new Task(srs.getLong("id"),
                        srs.getString("title"),
                        srs.getString("description"),
                        srs.getDate("due_date"),
                        srs.getString("status"),
                        srs.getLong("user_id")));
                        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        Task task = null;

        try {
            SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
            if (srs.next()) {
                task = new Task(srs.getLong("id"),
                        srs.getString("title"),
                        srs.getString("description"),
                        srs.getDate("due_date"),
                        srs.getString("status"),
                        srs.getLong("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(task);
    }

    @Override
    public void save(Task task) {
        String sql = "INSERT INTO tasks (title, description, due_date, status, user_id) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplateObject.update(sql, task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ? WHERE id = ?";
        try {
            jdbcTemplateObject.update(sql, task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long taskId, Long userId) {
        String sql = "DELETE FROM tasks WHERE id = ? AND user_id = ?";
        try {
            jdbcTemplateObject.update(sql, taskId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
