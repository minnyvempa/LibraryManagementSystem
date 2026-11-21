import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private ReturnService returnService;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        returnService = context.getBean(ReturnService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int transactionId = Integer.parseInt(
                request.getParameter("transactionId")
            );
            if (transactionId <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transaction ID must be positive.");
                return;
            }

            boolean result = returnService.returnBook(transactionId);

            if (result) {
                response.getWriter().println("Book Returned Successfully!");
            } else {
                response.getWriter().println("Transaction not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Book Return Failed");
        }
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.close();
        }
    }
}