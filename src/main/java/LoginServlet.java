import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        loginService = context.getBean(LoginService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Member member = loginService.login(email, password);

            if (member != null) {
                HttpSession session = request.getSession();
                session.setAttribute("memberId", member.getId());
                session.setAttribute("memberName", member.getName());
                session.setAttribute("memberEmail", member.getEmail());

                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("login.html?error=invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Login Failed");
        }
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.close();
        }
    }
}