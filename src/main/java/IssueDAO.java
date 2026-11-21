import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class IssueDAO {

    public boolean issueBook(int memberId, int bookId,
                             String issueDate, String returnDate) throws Exception {

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false);

            String updateBook =
                "UPDATE books SET Available = Available - 1 " +
                "WHERE id = ? AND Available > 0";

            try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                ps.setInt(1, bookId);

                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            String sql =
                "INSERT INTO transactions " +
                "(member_id, book_id, issue_date, return_date) " +
                "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, memberId);
                ps.setInt(2, bookId);
                ps.setDate(3, Date.valueOf(issueDate));

                if (returnDate == null || returnDate.trim().isEmpty()) {
                    ps.setNull(4, java.sql.Types.DATE);
                } else {
                    ps.setDate(4, Date.valueOf(returnDate));
                }

                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            con.rollback();
            throw e;

        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
}