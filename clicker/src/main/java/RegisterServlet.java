import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO instructors (email, phoneNumber, password) VALUES (?, ?, ?)")) {

            pstmt.setString(1, email);
            pstmt.setString(2, phoneNumber);
            pstmt.setString(3, password);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Registration successful, redirect to login page
                response.sendRedirect("login.jsp");
            } else {
                // Handle the error scenario, maybe show a message to the user
                response.sendRedirect("register.jsp?error=true"); // Use query string to indicate error
            }
        } catch (SQLException e) {
            // Log the exception (e.g., log to a file, send an email alert...)
            throw new ServletException("SQL error during registration", e);
        }
    }
}