package com.switchfully.digibooky.repository;

import com.switchfully.digibooky.domain.Author;
import com.switchfully.digibooky.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRepositoryTest {

    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository = new BookRepository();

        bookRepository.saveBook(new Book("978-1-11111-111-1", new Author("J.K.", "Rowling"), "Harry Potter and the Philosopher's Stone", "The first book in the Harry Potter series"));
        bookRepository.saveBook(new Book("978-2-22222-222-2", new Author("J.R.R.", "Tolkien"), "The Lord of the Rings", "Epic fantasy novel"));
        bookRepository.saveBook(new Book("978-3-33333-333-3", new Author("George", "Orwell"), "1984", "Dystopian social science fiction"));
        bookRepository.saveBook(new Book("978-4-44444-444-4", new Author("Jane", "Austen"), "Pride and Prejudice", "Romantic novel"));
        bookRepository.saveBook(new Book("978-5-55555-555-5", new Author("Stephen", "King"), "The Shining", "Horror novel"));
    }

    @Test
    void searchBooksByIsbn_withExactIsbn_shouldReturnMatchingBook() {
        String isbn = "978-1-11111-111-1";

        List<Book> books = bookRepository.searchBooksByIsbn(isbn);

        assertEquals(1, books.size());
        assertEquals("Harry Potter and the Philosopher's Stone", books.get(0).getTitle());
    }

    @Test
    void searchBooksByIsbn_withWildcard_shouldReturnMatchingBooks() {
        String isbn = "978-*";

        List<Book> books = bookRepository.searchBooksByIsbn(isbn);
        assertEquals(8, books.size());
        // 3 more books initialized in the repository
    }

    @Test
    void searchBooksByIsbn_withWildcardInMiddle_shouldReturnMatchingBooks() {
        String isbn = "978-*-111-1";

        List<Book> books = bookRepository.searchBooksByIsbn(isbn);
        assertEquals(1, books.size());
        assertEquals("978-1-11111-111-1", books.get(0).getIsbn());
    }

    @Test
    void searchBooksByIsbn_withSingleCharacterWildcard_shouldReturnMatchingBooks() {
        String isbn = "978-?-?????-???-?";

        List<Book> books = bookRepository.searchBooksByIsbn(isbn);
        assertEquals(5, books.size());
    }

    @Test
    void searchBooksByTitle_withExactTitle_shouldReturnMatchingBook() {
        String title = "The Shining";

        List<Book> books = bookRepository.searchBooksByTitle(title);
        assertEquals(1, books.size());
        assertEquals("The Shining", books.get(0).getTitle());
    }

    @Test
    void searchBooksByTitle_withWildcard_shouldReturnMatchingBooks() {
        String title = "The*";

        List<Book> books = bookRepository.searchBooksByTitle(title);
        assertEquals(3, books.size());
    }

    @Test
    void searchBooksByTitle_withWildcardInMiddle_shouldReturnMatchingBooks() {
        String title = "*and*";

        List<Book> books = bookRepository.searchBooksByTitle(title);
        assertEquals(2, books.size());
    }

    @Test
    void searchBookByIsbn_withExactIsbn_shouldReturnMatchingBook() {
        String title = "The Lord o? the Rings";

        List<Book> books = bookRepository.searchBooksByTitle(title);
        assertEquals("The Lord of the Rings",books.get(0).getTitle());
    }

    @Test
    void searchBookByTitle_withSensitiveCase_shouldReturnMatchingBooks() {
        String title = "harry*";

        List<Book> books = bookRepository.searchBooksByTitle(title);
        assertEquals("Harry Potter and the Philosopher's Stone", books.get(0).getTitle());
    }

    void searchBooksByAuthor_withFirstName_shouldReturnMatchingBooks() {
        String authorPattern = "George";

        List<Book> result = bookRepository.searchBooksByAuthor(authorPattern);

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
    }

    @Test
    void searchBooksByAuthor_withLastName_shouldReturnMatchingBooks() {
        String authorPattern = "King";

        List<Book> result = bookRepository.searchBooksByAuthor(authorPattern);

        assertEquals(1, result.size());
        assertEquals("The Shining", result.get(0).getTitle());
    }

    @Test
    void searchBooksByAuthor_withPartialName_shouldReturnMatchingBooks() {
        String authorPattern = "J*";

        List<Book> result = bookRepository.searchBooksByAuthor(authorPattern);

        assertEquals(6, result.size());
    }

    @Test
    void searchBooksByAuthor_withFullName_shouldReturnMatchingBooks() {
        String authorPattern = "J.K. Rowling";

        List<Book> result = bookRepository.searchBooksByAuthor(authorPattern);

        assertEquals(1, result.size());
        assertEquals("Harry Potter and the Philosopher's Stone", result.get(0).getTitle());
    }

    @Test
    void searchBooksByAuthor_withWildcardInName_shouldReturnMatchingBooks() {
        String authorPattern = "*Ro*";

        List<Book> result = bookRepository.searchBooksByAuthor(authorPattern);

        assertEquals(1, result.size());
    }
}
