import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private RegisterService registerService;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        registerService = context.getBean(RegisterService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (name == null || email == null || password == null ||
                name.trim().length() < 2 || password.length() < 8 ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                response.sendRedirect("register.html?error=invalid");
                return;
            }

            boolean result = registerService.register(name, email, password);

            if (result) {
                response.sendRedirect("login.html?registered=success");
            } else {
                response.sendRedirect("register.html?error=exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Registration Failed");
        }
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.close();
        }
    }
}