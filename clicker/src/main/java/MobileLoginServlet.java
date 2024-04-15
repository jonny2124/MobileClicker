import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/mobilelogin")
public class MobileLoginServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT id FROM students WHERE email = ? AND password = ?")) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("id");
                response.getWriter().write(String.valueOf(studentId));
            } else {
                response.getWriter().write("Invalid login credentials");
            }
        } catch (SQLException e) {
            response.getWriter().write("SQL error: " + e.getMessage());
        }
    }
}
