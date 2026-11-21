import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet(name = "IssueServlet", urlPatterns = {"/issue"})
public class IssueServlet extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private IssueService issueService;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        issueService = context.getBean(IssueService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int memberId = Integer.parseInt(request.getParameter("member"));
            int bookId = Integer.parseInt(request.getParameter("book"));

            String issueDate = request.getParameter("issueDate");
            String returnDate = request.getParameter("returnDate");

            if (memberId <= 0 || bookId <= 0 || issueDate == null || issueDate.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enter valid member, book and issue date.");
                return;
            }
            LocalDate parsedIssueDate = LocalDate.parse(issueDate);
            if (returnDate != null && !returnDate.isBlank()
                    && LocalDate.parse(returnDate).isBefore(parsedIssueDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Return date cannot be before issue date.");
                return;
            }

            boolean result = issueService.issueBook(
                memberId, bookId, issueDate, returnDate
            );

            if (result) {
                response.getWriter().println("Book Issued Successfully!");
            } else {
                response.getWriter().println("Book Not Available!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Book Issue Failed");
        }
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.close();
        }
    }
}