package ru.otus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.dao.AuthorDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.service.BookServiceImpl;

@JdbcTest
@Import(BookServiceImpl.class)
public class BookServiceTest {

  private static final String EXISTED_BOOK_NAME = "War and peace";
  private static final String EXISTED_BOOK_AUTHOR_1 = "Ivan";
  private static final String EXISTED_BOOK_AUTHOR_2 = "Mary";
  private static final String EXISTED_BOOK_GENRE_1 = "detective";
  private static final String EXISTED_BOOK_GENRE_2 = "history";
  private static final String NEW_BOOK_NAME = "Bestseller";
  @Autowired
  private BookServiceImpl bookService;

  @MockBean
  private BookDaoJdbc bookDaoJdbc;

  @MockBean
  private GenreDaoJdbc genreDaoJdbc;

  @MockBean
  private AuthorDaoJdbc authorDaoJdbc;

  @Test
  public void createBookTest() {
    BookDto expectedBook = new BookDto(NEW_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    Book mockBook = new Book(3,NEW_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1), new Genre(3, EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDaoJdbc.getByName(NEW_BOOK_NAME)).thenReturn(mockBook);
    bookService.save(expectedBook);
    BookDto actualBook = bookService.getBook(expectedBook.getName());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Получение списка книг")
  public void getAllBooksTest() {
    Book mockBook = new Book(3,EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1), new Genre(3, EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDaoJdbc.getAll()).thenReturn(List.of(mockBook));
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<BookDto> actualBooks = bookService.getAllBooks();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Получение книги")
  public void getBookByNameTest() {
    Book mockBook = new Book(3,EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1), new Genre(3, EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDaoJdbc.getByName(EXISTED_BOOK_NAME)).thenReturn(mockBook);
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME);
    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Удаление книги")
  public void deleteBookTest() {
    Book mockBook = new Book(3,EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1), new Genre(3, EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDaoJdbc.getByName(EXISTED_BOOK_NAME)).thenReturn(mockBook);
    assertThatCode(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .doesNotThrowAnyException();
    bookService.delete(EXISTED_BOOK_NAME);
    Mockito.when(bookDaoJdbc.getByName(EXISTED_BOOK_NAME)).thenThrow(new EmptyResultDataAccessException(9));
    assertThatThrownBy(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @Test
  @DisplayName("обновление книги")
  public void updateBookTest() {
    Mockito.when(genreDaoJdbc.getByName(EXISTED_BOOK_GENRE_2)).thenReturn(new Genre(3,EXISTED_BOOK_GENRE_2));
    Mockito.when(authorDaoJdbc.getByName(EXISTED_BOOK_AUTHOR_2)).thenReturn(new Author(3, EXISTED_BOOK_AUTHOR_2));
    Mockito.when(bookDaoJdbc.getByName(EXISTED_BOOK_NAME)).thenReturn(new Book(1,EXISTED_BOOK_NAME, new Author(1, EXISTED_BOOK_AUTHOR_1),
        new Genre(1, EXISTED_BOOK_GENRE_1)));

    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);
    bookService.update(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);

    Mockito.when(bookDaoJdbc.getByName(EXISTED_BOOK_NAME)).thenReturn(new Book(1,EXISTED_BOOK_NAME, new Author(2, EXISTED_BOOK_AUTHOR_2),
        new Genre(2, EXISTED_BOOK_GENRE_2)));
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

}
