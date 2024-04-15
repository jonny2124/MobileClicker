import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sqlStr = "SELECT id FROM instructors WHERE email = ? AND password = ? LIMIT 1";
            pstmt = conn.prepareStatement(sqlStr);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // User exists with the given email and password
                HttpSession session = request.getSession();
                session.setAttribute("teacherId", rs.getInt("id"));

                // Redirect to the home page or dashboard
                response.sendRedirect("home");
            } else {
                // User does not exist, handle login failure
                request.setAttribute("loginError", "Invalid email or password, please register!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new ServletException("SQLException occurred", e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Use proper logging
            }
        }
    }
}
