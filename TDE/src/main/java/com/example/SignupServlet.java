package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:mysql://localhost:3306/login_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "210904";
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password) VALUES (?, ?)";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("signup.html?error=1");
            return;
        }

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                response.sendRedirect("signup.html?success=1");
            } else {
                response.sendRedirect("signup.html?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup.html?error=1");
        }
    }
}
