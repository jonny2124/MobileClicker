import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacherId") != null) {
            List<QuestionBank> questionBanks = fetchQuestionBanks();
            request.setAttribute("questionBanks", questionBanks);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private List<QuestionBank> fetchQuestionBanks() {
        List<QuestionBank> questionBanks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM question_banks")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuestionBank questionBank = new QuestionBank(
                        rs.getInt("bank_id"),
                        rs.getString("title"),
                        rs.getString("subject"),
                        rs.getInt("difficulty"),
                        rs.getString("color_scheme"),
                        rs.getInt("time_per_question"));
                questionBanks.add(questionBank);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper logging should be implemented instead of printing the stack trace
        }
        return questionBanks;
    }

    // Inner class to hold question bank data
    public class QuestionBank {
        private int bankId;
        private String title;
        private String subject;
        private int difficulty;
        private String colorScheme;
        private int timePerQuestion;

        public QuestionBank(int bankId, String title, String subject, int difficulty, String colorScheme,
                int timePerQuestion) {
            this.bankId = bankId;
            this.title = title;
            this.subject = subject;
            this.difficulty = difficulty;
            this.colorScheme = colorScheme;
            this.timePerQuestion = timePerQuestion;
        }

        // Public getter methods
        public int getBankId() {
            return bankId;
        }

        public String getTitle() {
            return title;
        }

        public String getSubject() {
            return subject;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public String getColorScheme() {
            return colorScheme;
        }

        public int getTimePerQuestion() {
            return timePerQuestion;
        }
    }
}
