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

@WebServlet("/questionBank")
public class QuestionBankServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forwards to the question bank creation form
        request.getRequestDispatcher("/questionBankCreation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String subject = request.getParameter("subject");
        int difficulty = Integer.parseInt(request.getParameter("difficulty"));
        String colorScheme = request.getParameter("colorScheme");
        int timePerQuestion = Integer.parseInt(request.getParameter("timePerQuestion"));

        if (title == null || subject == null || colorScheme == null
                || title.isEmpty() || subject.isEmpty() || colorScheme.isEmpty()
                || difficulty < 1 || difficulty > 5 || timePerQuestion < 1) {
            request.setAttribute("error", "Invalid input. Please fill all fields correctly.");
            request.getRequestDispatcher("/questionBankCreation.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO question_banks (title, subject, difficulty, color_scheme, time_per_question) VALUES (?, ?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, title);
            pstmt.setString(2, subject);
            pstmt.setInt(3, difficulty);
            pstmt.setString(4, colorScheme);
            pstmt.setInt(5, timePerQuestion);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bankId = generatedKeys.getInt(1);
                    response.sendRedirect("questionManagement?bankId=" + bankId);
                } else {
                    request.setAttribute("error", "Unable to get question bank ID. Please try again.");
                    request.getRequestDispatcher("/questionBankCreation.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Unable to create question bank.");
                request.getRequestDispatcher("/questionBankCreation.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again.");
            request.getRequestDispatcher("/questionBankCreation.jsp").forward(request, response);
        }
    }
}
