public class IssueService {

    private IssueDAO issueDAO;

    public IssueService(IssueDAO issueDAO) {
        this.issueDAO = issueDAO;
    }

    public boolean issueBook(int memberId, int bookId,
                             String issueDate, String returnDate) throws Exception {

        return issueDAO.issueBook(memberId, bookId, issueDate, returnDate);
    }
}