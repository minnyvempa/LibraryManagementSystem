public class ReturnService {

    private ReturnDAO returnDAO;

    public ReturnService(ReturnDAO returnDAO) {
        this.returnDAO = returnDAO;
    }

    public boolean returnBook(int transactionId) throws Exception {
        return returnDAO.returnBook(transactionId);
    }
}