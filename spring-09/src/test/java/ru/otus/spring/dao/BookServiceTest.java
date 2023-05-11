package ru.otus.spring.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.dao.PersonDaoJdbc;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

@JdbcTest
@Import({BookService.class, BookDaoJdbc.class, PersonDaoJdbc.class, GenreDaoJdbc.class})
public class BookServiceTest {

  private static final String EXISTED_BOOK_NAME = "War and peace";
  private static final String EXISTED_BOOK_AUTHOR_1 = "Ivan";
  private static final String EXISTED_BOOK_AUTHOR_2 = "Mary";
  private static final String EXISTED_BOOK_GENRE_1 = "detective";
  private static final String EXISTED_BOOK_GENRE_2 = "history";
  private static final String NEW_BOOK_NAME = "Bestseller";
  @Autowired
  private BookService bookService;

  @Test
  public void createBookTest() {
    Book expectedBook = new Book(NEW_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    bookService.save(expectedBook);

    Book actualBook = bookService.getBook(expectedBook.getName());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Получение списка книг")
  public void getAllBooksTest() {
    Book expectedBook = new Book(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<Book> actualBooks = bookService.getAllBooks();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Получение книги")
  public void getBookByNameTest() {
    Book expectedBook = new Book(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    Book actualBook = bookService.getBook(EXISTED_BOOK_NAME);
    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Удаление книги")
  public void deleteBookTest() {
    assertThatCode(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .doesNotThrowAnyException();
    bookService.delete(EXISTED_BOOK_NAME);
    assertThatThrownBy(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @Test
  @DisplayName("обновление книги")
  public void updateBookTest() {
    Book expectedBook = new Book(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);
    bookService.update(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);
    Book actualBook = bookService.getBook(EXISTED_BOOK_NAME);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

}
