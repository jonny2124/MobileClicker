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

@WebServlet("/questionDisplay")
public class QuestionDisplayServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bankId = request.getParameter("bankId");
        if (bankId == null || bankId.isEmpty()) {
            response.sendRedirect("home?error=Missing question bank ID");
            return;
        }
        // Fetch the first question of the question bank
        Question question = fetchQuestion(bankId, 0); // Assuming that the question IDs start at 1
        if (question != null) {
            request.getSession().setAttribute("currentQuestion", question);
            request.getRequestDispatcher("questionDisplay.jsp").forward(request, response);
        } else {
            // Handle case where no questions could be found
            response.sendRedirect("home?error=No questions found for the selected question bank.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bankId = request.getParameter("bankId");
        String currentQuestionId = request.getParameter("currentQuestionId");

        if (bankId == null || currentQuestionId == null) {
            response.sendRedirect("home?error=Missing parameter");
            return;
        }

        // Fetch the next question based on the current question ID
        int nextQuestionId = Integer.parseInt(currentQuestionId);
        Question nextQuestion = fetchQuestion(bankId, nextQuestionId);
        if (nextQuestion != null) {
            request.getSession().setAttribute("currentQuestion", nextQuestion);
            request.getRequestDispatcher("questionDisplay.jsp").forward(request, response);
        } else {
            // No more questions, quiz is over
            request.setAttribute("quizOver", true);
            request.getSession().removeAttribute("currentQuestion");
            request.getRequestDispatcher("questionDisplay.jsp").forward(request, response);

        }
    }

    private Question fetchQuestion(String bankIdString, int questionId) {
        Question question = null;
        try {
            int bankId = Integer.parseInt(bankIdString);
            String sql = "SELECT * FROM questions WHERE bank_id = ? AND question_id > ? ORDER BY question_id LIMIT 1";

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, bankId);
                pstmt.setInt(2, questionId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    question = new Question(
                            rs.getInt("question_id"),
                            bankId,
                            rs.getString("question_text"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option"));
                }
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace(); // Proper logging should be implemented
        }
        return question;
    }

    // Inner class to hold question data
    public static class Question {
        private final int questionId;
        private final int bankId;
        private final String questionText;
        private final String optionA;
        private final String optionB;
        private final String optionC;
        private final String optionD;
        private final String correctOption;

        public Question(int questionId, int bankId, String questionText, String optionA, String optionB, String optionC,
                String optionD, String correctOption) {
            this.questionId = questionId;
            this.bankId = bankId;
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctOption = correctOption;
        }

        public int getQuestionId() {
            return questionId;
        }

        public int getBankId() {
            return bankId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public String getCorrectOption() {
            return correctOption;
        }
    }
}
