package com.taskplannersql.service;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс UserService служит для проверки авторизации пользователя или его регистрации.
 */
public class UserService {
    private static final String URL = "jdbc:postgresql://localhost:5432/tasks";
    private static final String ROOT_NAME = "task";
    private static final String SQL_PASSWORD = "mypassword";

    /**
     * Делает запрос в базу данных для проверки пользователя
     *
     * @return возвращает список пользователей в сервлет
     */
    public static ArrayList<String> checkUser() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM users");
            while (rs.next()) {
                String x = rs.getString("username");
                list.add(x);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    /**
     * Проверяет пароль пользователя
     */
    public static String checkPassword(String login) {
        String basedPassword = "";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "SELECT password FROM users WHERE username = ?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, login);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                basedPassword = rs.getString("password");
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
        return basedPassword;
    }

    /**
     * Добавляет логин и пароль нового пользователя
     */
    public static void addUser(String login, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, ROOT_NAME, SQL_PASSWORD);
            String sql = "INSERT INTO users (username, password) Values (?, ?)";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setString(1, login);
            prst.setString(2, password);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
}
