import java.util.List;

public class BookService {

    private BookDAO bookDAO;

    // Spring yahan BookDAO daalega (Dependency Injection)
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
}