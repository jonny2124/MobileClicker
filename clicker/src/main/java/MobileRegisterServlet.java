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

@WebServlet("/mobileregister")
public class MobileRegisterServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO students (email, phoneNumber, password) VALUES (?, ?, ?)")) {
            pstmt.setString(1, email);
            pstmt.setString(2, phoneNumber);
            pstmt.setString(3, password);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                response.getWriter().write("Registration successful");
            } else {
                response.getWriter().write("Registration failed");
            }
        } catch (SQLException e) {
            response.getWriter().write("SQL error: " + e.getMessage());
        }
    }
}
