import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDAO {

    public boolean register(String name, String email, String password)
            throws Exception {

        try (Connection con = DBConnection.getConnection()) {

            String checkSql = "SELECT id FROM members WHERE email = ?";

            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setString(1, email);

                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }

            String sql = "INSERT INTO members (name, email, password) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                return ps.executeUpdate() > 0;
            }
        }
    }
}