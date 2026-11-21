public class RegisterService {

    private RegisterDAO registerDAO;

    public RegisterService(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }

    public boolean register(String name, String email, String password)
            throws Exception {

        if (name == null || name.trim().length() < 2 ||
            email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") ||
            password == null || password.length() < 8) {
            return false;
        }

        return registerDAO.register(name.trim(), email.trim(), password);
    }
}