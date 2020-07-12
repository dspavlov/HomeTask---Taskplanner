package com.taskplannersql.service;

import com.taskplannersql.beans.Task;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс TaskService служит для выполнения необходимых операций с задачами:
 * -просмотр всех задач из БД;
 * -добавление задачи в БД;
 * -редактирование задачи в БД;
 * -вывод данных задачи по ID.
 */
public class TaskService {
    private static final String URL = "jdbc:postgresql://localhost:5432/tasks";
    private static final String ROOT_NAME = "task";
    private static final String SQL_PASSWORD = "mypassword";

    /**
     * Возвращает список всех задач пользователя
     *
     * @param user - пользователь сервиса
     * @return список задач
     */
    public static ArrayList<Task> readAllTasks(String user) {
        ArrayList<Task> tasksFromSql = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "SELECT * from tasks WHERE taskowner = ?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, user);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("text");
                String description = rs.getString("description");
                java.util.Date dateofend = rs.getDate("dateofend");
                boolean isdone = rs.getBoolean("isdone");
                String idofgoal = rs.getString("idofgoal");
                String idsubgoal = rs.getString("idsubgoal");
                Task task = new Task(id, text, description, dateofend, isdone, idofgoal, idsubgoal);
                tasksFromSql.add(task);
            }
            prst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        return tasksFromSql;
    }

    /**
     * Создает новоую задачу
     *
     * @param text        - задача для создания
     * @param description - описание задачи
     * @param dateOfEnd   срок выполнения задачи
     * @param user        - пользователь сервиса
     */
    public static void createTask(String text, String description, String dateOfEnd, String idOfGoal, String idOfSubGoal, String user) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "INSERT INTO tasks (text, description, dateOfEnd, isdone, idofgoal, idsubgoal, taskowner) Values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, text);
            prst.setString(2, description);
            prst.setDate(3, Date.valueOf(dateOfEnd));
            prst.setBoolean(4, false);
            prst.setString(5, idOfGoal);
            prst.setString(6, idOfSubGoal);
            prst.setString(7, user);
            prst.executeUpdate();
            prst.close();
        } catch (IllegalArgumentException | SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Удаляет задачу по ID
     *
     * @param id - id задачи, которую нужно удалить
     */
    public static void deleteTask(int id) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement pest = conn.prepareStatement(sql);
            pest.setInt(1, id);
            pest.executeUpdate();
            pest.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Редактирует задачу с заданным id,
     * в соответствии с переданными параметрами задачи
     *
     * @param idTask      - id задачи которую нужно обновить
     * @param text        - текст задачи
     * @param description - описание задачи
     * @param dateOfEnd   - срок выполнения задачи
     * @param isDone      - статус задачи (выполена/в работе)
     */
    public static void edit(int idTask, String text, String description, String dateOfEnd, boolean isDone) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "UPDATE tasks SET text = ?, description = ?, dateofend = ?, isdone = ? WHERE id = ?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, text);
            prst.setString(2, description);
            prst.setDate(3, Date.valueOf(dateOfEnd));
            prst.setBoolean(4, isDone);
            prst.setInt(5, idTask);
            prst.executeUpdate();
            prst.close();
        } catch (IllegalArgumentException | SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Возвращает задачу по ее ID
     *
     * @param idTask - ID задачи
     * @return объект задачи с заданным ID
     */
    public static Task read(int idTask) {
        Task task = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "SELECT * FROM tasks WHERE id = ?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setInt(1, idTask);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("text");
                String description = rs.getString("description");
                java.util.Date dateOfEnd = rs.getDate("dateofend");
                boolean isdone = rs.getBoolean("isdone");
                String idofgoal = rs.getString("idofgoal");
                String idsubgoal = rs.getString("idsubgoal");
                task = new Task(id, text, description, dateOfEnd, isdone, idofgoal, idsubgoal);
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        return task;
    }
}