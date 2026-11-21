public class LoginService {

    private LoginDAO loginDAO;

    public LoginService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public Member login(String email, String password) throws Exception {
        return loginDAO.login(email, password);
    }
}