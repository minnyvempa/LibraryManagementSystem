import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Optional Swing desktop dashboard for the existing Library Management System.
 * It reuses the existing Spring BookService and JDBC/DAO layer.
 *
 * Run from Eclipse: Right click this file > Run As > Java Application.
 */
public class LibrarySwingDashboard extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new Object[] {"Book ID", "Title", "Author", "Category", "Available Copies"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final AnnotationConfigApplicationContext springContext;
    private final BookService bookService;
    private final JLabel statusLabel = new JLabel("Ready");

    public LibrarySwingDashboard() {
        super("Library Management System - Swing Dashboard");

        springContext = new AnnotationConfigApplicationContext(AppConfig.class);
        bookService = springContext.getBean(BookService.class);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JLabel heading = new JLabel("Library Management System", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 23));
        add(heading, BorderLayout.NORTH);

        JTable bookTable = new JTable(tableModel);
        bookTable.setRowHeight(25);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Books");
        refreshButton.addActionListener(event -> loadBooks());

        JButton openWebButton = new JButton("Open Web Application");
        openWebButton.addActionListener(event -> {
            try {
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().browse(
                        URI.create("http://localhost:8080/LibraryManagementSystem/index.html")
                    );
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Open http://localhost:8080/LibraryManagementSystem/index.html in your browser.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Could not open browser: " + ex.getMessage(),
                    "Browser Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(event -> dispose());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        controls.add(refreshButton);
        controls.add(openWebButton);
        controls.add(closeButton);

        JPanel south = new JPanel(new BorderLayout());
        south.add(controls, BorderLayout.CENTER);
        south.add(statusLabel, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                springContext.close();
            }
        });

        loadBooks();
    }

    private void loadBooks() {
        statusLabel.setText("Loading books from MySQL...");
        new SwingWorker<List<Book>, Void>() {
            @Override
            protected List<Book> doInBackground() {
                return bookService.getAllBooks();
            }

            @Override
            protected void done() {
                try {
                    List<Book> books = get();
                    tableModel.setRowCount(0);
                    for (Book book : books) {
                        tableModel.addRow(new Object[] {
                            book.getId(), book.getTitle(), book.getAuthor(),
                            book.getCategory(), book.getAvailable()
                        });
                    }
                    statusLabel.setText("Loaded " + books.size() + " book record(s).");
                } catch (Exception ex) {
                    statusLabel.setText("Could not load books. Check MySQL connection.");
                    JOptionPane.showMessageDialog(LibrarySwingDashboard.this,
                        "Unable to load books: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Swing needs a desktop environment to display its window.");
            return;
        }
        javax.swing.SwingUtilities.invokeLater(() ->
            new LibrarySwingDashboard().setVisible(true)
        );
    }
}
