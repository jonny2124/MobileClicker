import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/submitResponse")
public class SubmitResponseServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx"; // Replace with your actual password

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int questionNo = Integer.parseInt(request.getParameter("questionNo"));
        String choice = request.getParameter("choice");
        // For now, we're not using studentID, but it's ready for when we need it
        // int studentID = Integer.parseInt(request.getParameter("studentID"));

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn
                        .prepareStatement("INSERT INTO responses (questionNo, choice) VALUES (?, ?)")) {
            pstmt.setInt(1, questionNo);
            pstmt.setString(2, choice);
            pstmt.executeUpdate();
            response.getWriter().write("Response recorded successfully");
        } catch (SQLException e) {
            throw new ServletException("SQL error when saving response", e);
        }
    }
}
