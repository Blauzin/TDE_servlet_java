package com.gradecalculator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/api/students/*")
public class LoadStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getPathInfo().substring(1);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(studentId));
            ResultSet rs = stmt.executeQuery();

            Student student = null;
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));

                String subjectQuery = "SELECT id, name FROM Subjects WHERE student_id = ?";
                PreparedStatement subjectStmt = conn.prepareStatement(subjectQuery);
                subjectStmt.setInt(1, student.getId());
                ResultSet subjectRs = subjectStmt.executeQuery();

                List<Subject> subjects = new ArrayList<>();
                while (subjectRs.next()) {
                    Subject subject = new Subject();
                    subject.setId(subjectRs.getInt("id"));
                    subject.setName(subjectRs.getString("name"));
                    subjects.add(subject);
                }
                student.setSubjects(subjects);
            }

            String json = new Gson().toJson(student);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
