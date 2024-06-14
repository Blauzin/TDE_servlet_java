package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/login_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "210904";

    private static final String SELECT_USER_BY_USERNAME = "SELECT id, username, password FROM users WHERE username = ?";

    public User selectUser(String username) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                String password = rs.getString("password");
                user = new User();
                user.setId(id);
                user.setUsername(uname);
                user.setPassword(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

