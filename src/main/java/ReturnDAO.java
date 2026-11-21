import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnDAO {

    public boolean returnBook(int transactionId) throws Exception {

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false);

            int bookId;

            String findBook =
                "SELECT book_id FROM transactions WHERE id = ? FOR UPDATE";

            try (PreparedStatement ps = con.prepareStatement(findBook)) {
                ps.setInt(1, transactionId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }
                    bookId = rs.getInt("book_id");
                }
            }

            String updateBook =
                "UPDATE books SET Available = Available + 1 WHERE id = ?";

            try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            String deleteTransaction =
                "DELETE FROM transactions WHERE id = ?";

            try (PreparedStatement ps = con.prepareStatement(deleteTransaction)) {
                ps.setInt(1, transactionId);
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