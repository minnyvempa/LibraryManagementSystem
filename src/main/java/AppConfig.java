import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Book List
    @Bean
    public BookDAO bookDAO() {
        return new BookDAO();
    }

    @Bean
    public BookService bookService() {
        return new BookService(bookDAO());
    }

    // Issue Book
    @Bean
    public IssueDAO issueDAO() {
        return new IssueDAO();
    }

    @Bean
    public IssueService issueService() {
        return new IssueService(issueDAO());
    }

    // Return Book
    @Bean
    public ReturnDAO returnDAO() {
        return new ReturnDAO();
    }

    @Bean
    public ReturnService returnService() {
        return new ReturnService(returnDAO());
    }

    // Login
    @Bean
    public LoginDAO loginDAO() {
        return new LoginDAO();
    }

    @Bean
    public LoginService loginService() {
        return new LoginService(loginDAO());
    }

    // Register
    @Bean
    public RegisterDAO registerDAO() {
        return new RegisterDAO();
    }

    @Bean
    public RegisterService registerService() {
        return new RegisterService(registerDAO());
    }
}