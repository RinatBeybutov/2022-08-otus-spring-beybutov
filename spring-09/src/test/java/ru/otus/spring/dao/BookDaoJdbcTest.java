package ru.otus.spring.dao;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

  private static final long EXISTING_BOOK_ID = 1;
  private static final String EXISTING_BOOK_NAME = "War and peace";
  private static final long EXISTING_ID_AUTHOR = 1;
  private static final long EXISTING_ID_GENRE = 1;
  private static final int EXPECTED_BOOKS_COUNT = 1;
  @Autowired
  private BookDaoJdbc bookDao;

  @Test
  @DisplayName("Обновление книги")
  void updateBookTest() {
    BookDto expectedBook = new BookDto(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, 2,
        2);
    bookDao.updateByName(expectedBook);
    BookDto actualBook = bookDao.getByName(EXISTING_BOOK_NAME);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Вставка книги")
  void insertBookTest() {
    int countBeforeInsert = bookDao.count();
    assertThat(countBeforeInsert).isEqualTo(EXPECTED_BOOKS_COUNT);

    BookDto expectedBook = new BookDto(2, "New book", 1, 1);
    bookDao.insert(expectedBook);

    BookDto actualBook = bookDao.getById(expectedBook.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

  }

  @Test
  @DisplayName("Получение списка книг")
  void getBooksListTest() {
    BookDto expectedBook = new BookDto(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_ID_AUTHOR,
        EXISTING_ID_GENRE);
    List<BookDto> actualBooks = bookDao.getAll();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Поиск книги по Id")
  void getBookByIdTest() {
    BookDto expectedBook = new BookDto(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_ID_AUTHOR,
        EXISTING_ID_GENRE);
    BookDto actualBook = bookDao.getById(expectedBook.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Удаление книги")
  void deletePersonByIdTest() {
    assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
        .doesNotThrowAnyException();
    bookDao.deleteById(EXISTING_BOOK_ID);
    assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }
}
