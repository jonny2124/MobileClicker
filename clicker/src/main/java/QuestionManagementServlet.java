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

@WebServlet("/questionManagement")
public class QuestionManagementServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forwards to the question addition form
        String bankId = request.getParameter("bankId");
        if (bankId == null || bankId.isEmpty()) {
            // Redirect or show error if bankId is missing
            response.sendRedirect("home?error=Missing question bank ID");
            return;
        }
        request.setAttribute("bankId", bankId);
        request.getRequestDispatcher("/questionAddition.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bankId = request.getParameter("bankId");
        String questionText = request.getParameter("questionText");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String correctOption = request.getParameter("correctOption");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO questions (bank_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setInt(1, Integer.parseInt(bankId));
            pstmt.setString(2, questionText);
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setString(7, correctOption);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                response.sendRedirect("questionManagement?success=true&bankId=" + bankId);
            } else {
                request.setAttribute("error", "Unable to add question. Please try again.");
                request.getRequestDispatcher("/questionAddition.jsp").forward(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Invalid input or database error. Please try again.");
            request.getRequestDispatcher("/questionAddition.jsp").forward(request, response);
        }
    }
}
