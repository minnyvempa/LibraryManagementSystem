import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        // Spring start hota hai aur BookService (BookDAO ke saath) tayyar karta hai
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        bookService = context.getBean(BookService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("booklist.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.close();
        }
    }
}