package service;

import entity.Book;
import repository.BookRepository;

import java.sql.SQLException;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();

    public void addBook(String id, String title, String author, String category,
                        String yearText, String qtyText) throws SQLException {
        if (id.trim().isEmpty() || title.trim().isEmpty() || author.trim().isEmpty()
                || category.trim().isEmpty() || yearText.trim().isEmpty() || qtyText.trim().isEmpty()) {
            throw new IllegalArgumentException("Fill in all the fields");
        }

        int year;
        int qty;
        try {
            year = Integer.parseInt(yearText.trim());
            qty = Integer.parseInt(qtyText.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year and quantity must be numbers");
        }

        if (year < 1000 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Enter a valid published year");
        }
        if (qty < 1) {
            throw new IllegalArgumentException("Quantity must be 1 or more");
        }
        if (bookRepository.exists(id.trim())) {
            throw new IllegalArgumentException("Book ID " + id.trim() + " already exists");
        }

        bookRepository.save(new Book(id.trim(), title.trim(), author.trim(), category.trim(), year, qty));
    }
}