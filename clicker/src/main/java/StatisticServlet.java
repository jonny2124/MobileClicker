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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/statistic")
public class StatisticServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker_app?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "myuser";
    private static final String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int largestBankId = getLargestBankId();

        Map<Integer, Integer> statisticMap = getStatisticsForBank(largestBankId);

        request.setAttribute("statisticMap", statisticMap);
        request.getRequestDispatcher("/statistic.jsp").forward(request, response);
    }

    private int getLargestBankId() {
        int largestBankId = -1;
        String sql = "SELECT MAX(bank_id) AS largest_bank_id FROM question_banks";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                largestBankId = rs.getInt("largest_bank_id");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the error properly
        }
        return largestBankId;
    }

    private Map<Integer, Integer> getStatisticsForBank(int bankId) {
        Map<Integer, Integer> stats = new HashMap<>();
        String sql = "SELECT questionNo, COUNT(*) as count FROM responses WHERE questionNo IN (SELECT question_id FROM questions WHERE bank_id = ?) GROUP BY questionNo ORDER BY questionNo";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bankId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int questionNo = rs.getInt("questionNo");
                    int count = rs.getInt("count");
                    String width = calculateWidth(count); // Calculate width based on count
                    stats.put(questionNo, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the error properly
        }
        return stats;
    }

    private String calculateWidth(int count) {
        // Assuming maximum count is scaled to 100% width
        int maxCount = 100;
        int width = (count * 100) / maxCount; // Normalize the count to a percentage
        return width + "%"; // Return width in percentage
    }
}
