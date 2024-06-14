package com.gradecalculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

@WebServlet("/api/subjects/*")
public class LoadSubjectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getPathInfo().substring(1);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Subjects WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(subjectId));
            ResultSet rs = stmt.executeQuery();

            Subject subject = null;
            if (rs.next()) {
                subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));

                String raQuery = "SELECT * FROM RAs WHERE subject_id = ?";
                PreparedStatement raStmt = conn.prepareStatement(raQuery);
                raStmt.setInt(1, subject.getId());
                ResultSet raRs = raStmt.executeQuery();

                List<RA> ras = new ArrayList<>();
                while (raRs.next()) {
                    RA ra = new RA();
                    ra.setId(raRs.getInt("id"));
                    ra.setRaNumber(raRs.getInt("ra_number"));
                    ra.setWeight(raRs.getFloat("weight"));
                    ra.setScore(raRs.getFloat("score"));

                    String componentQuery = "SELECT * FROM Components WHERE ra_id = ?";
                    PreparedStatement componentStmt = conn.prepareStatement(componentQuery);
                    componentStmt.setInt(1, ra.getId());
                    ResultSet componentRs = componentStmt.executeQuery();

                    List<Component> components = new ArrayList<>();
                    while (componentRs.next()) {
                        Component component = new Component();
                        component.setId(componentRs.getInt("id"));
                        component.setComponentType(componentRs.getString("component_type"));
                        component.setWeight(componentRs.getFloat("weight"));
                        component.setScore(componentRs.getFloat("score"));
                        components.add(component);
                    }
                    ra.setComponents(components);
                    ras.add(ra);
                }
                subject.setRas(ras);
            }

            String json = new Gson().toJson(subject);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
