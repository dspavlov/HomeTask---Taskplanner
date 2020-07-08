package com.taskplannersql.service;

import com.taskplannersql.beans.Goal;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс GoalService служит для выполнения необходимых операций с целями:
 * -просмотр всех целей из БД;
 * -добавление цели в БД;
 * -редактирование цели в БД;
 */

public class GoalService {
    private static final String URL = "jdbc:postgresql://localhost:5432/tasks";
    private static final String ROOT_NAME = "task";
    private static final String SQL_PASSWORD = "mypassword";


    /**
     * Возвращает список всех целей пользователя
     *
     * @param user - пользователь сервиса
     * @return список целей
     */
    public static ArrayList<Goal> readGoals(String user){
        ArrayList<Goal> goalsFromSql = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "SELECT * FROM goals WHERE goalowner = ?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, user);
            ResultSet rs = prst.executeQuery();
            while (rs.next()){
                String goalName = rs.getString("goal");
                String subGoal = rs.getString("subgoal");
                Goal goal = new Goal(goalName, subGoal);
                goalsFromSql.add(goal);
            }
            prst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return goalsFromSql;
    }

    /**
     * Создает новоую цель
     *
     * @param goalName - название цели
     * @param user - авторизованый пользователь
     */
    public static void createGoal(String goalName, String user){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "INSERT INTO goals (goal, goalowner) VALUES (?, ?)";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, goalName);
            prst.setString(2, user);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Удаляет цель
     *
     * @param goalName - цель, которую нужно удалить
     */
    public static void deleteGoal(String goalName) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sqlgoals = "DELETE FROM goals WHERE goal = ?";
            String sqlsubgoals = "DELETE FROM subgoals WHERE idgoal=?";
            String sqltasks = "DELETE FROM tasks WHERE idofgoal=?";
            PreparedStatement prst1 = conn.prepareStatement(sqlgoals);
            PreparedStatement prst2 = conn.prepareStatement(sqlsubgoals);
            PreparedStatement prst3 = conn.prepareStatement(sqltasks);
            prst1.setString(1, goalName);
            prst2.setString(1, goalName);
            prst3.setString(1, goalName);
            prst1.executeUpdate();
            prst2.executeUpdate();
            prst3.executeUpdate();
            prst1.close();
            prst2.close();
            prst3.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * Редактирует цель.
     *
     * @param goalName - название цели
     * @param newGoalName - новое название цели
     */
    public static void editGoal(String goalName, String newGoalName) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sqlGoals = "UPDATE goals SET goal = ? WHERE goal = ?";
            String sqlSubGoals = "UPDATE subgoals SET idgoal = ? WHERE idgoal = ?";
            String sqlTasks = "UPDATE tasks SET idofgoal = ? WHERE idofgoal = ?";
            PreparedStatement prst1 = conn.prepareStatement(sqlGoals);
            PreparedStatement prst2 = conn.prepareStatement(sqlSubGoals);
            PreparedStatement prst3 = conn.prepareStatement(sqlTasks);
            prst1.setString(1, newGoalName);
            prst1.setString(2, goalName);
            prst2.setString(1,newGoalName);
            prst2.setString(2, goalName);
            prst3.setString(1,newGoalName);
            prst3.setString(2, goalName);
            prst1.executeUpdate();
            prst2.executeUpdate();
            prst3.executeUpdate();
            prst1.close();
            prst2.close();
            prst3.close();
        } catch (IllegalArgumentException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
