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

@WebServlet("/fetchQuestion")
public class FetchQuestionServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int currentQuestionNo = getCurrentQuestionNo();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(currentQuestionNo));
    }

    private int getCurrentQuestionNo() {
        // Initialize the default question number
        int currentQuestionNo = -1; // Assuming -1 indicates an error or uninitialized state

        // SQL query to find the maximum question_id from the questions table
        String sql = "SELECT MAX(question_id) AS max_question_id FROM questions";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            // Check if a maximum question_id was found
            if (rs.next()) {
                int maxQuestionId = rs.getInt("max_question_id");
                currentQuestionNo = maxQuestionId - 1; // Subtract 1 as per the requirement
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception; replace with better error handling in production
        }

        return currentQuestionNo;
    }
}
