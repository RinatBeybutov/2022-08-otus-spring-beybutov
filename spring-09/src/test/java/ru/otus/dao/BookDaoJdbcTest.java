package ru.otus.dao;

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
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

  private static final long EXISTING_BOOK_ID = 1;
  private static final String EXISTING_BOOK_NAME = "War and peace";
  private static final long EXISTING_ID_AUTHOR = 1;
  private static final String EXISTING_NAME_AUTHOR ="Ivan";
  private static final long EXISTING_ID_GENRE = 1;
  private static final String EXISTING_NAME_GENRE = "detective";
  private static final int EXPECTED_BOOKS_COUNT = 1;
  @Autowired
  private BookDaoJdbc bookDao;

  @Test
  @DisplayName("Обновление книги")
  void updateBookTest() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, new Author(2,"Mary"),
        new Genre(2, "history"));
    bookDao.updateByName(expectedBook);
    Book actualBook = bookDao.getByName(EXISTING_BOOK_NAME);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Вставка книги")
  void insertBookTest() {
    int countBeforeInsert = bookDao.count();
    assertThat(countBeforeInsert).isEqualTo(EXPECTED_BOOKS_COUNT);

    Book expectedBook = new Book(2, "New book", new Author(EXISTING_ID_AUTHOR,EXISTING_NAME_AUTHOR),
        new Genre(EXISTING_ID_GENRE, EXISTING_NAME_GENRE));
    bookDao.insert(expectedBook);

    Book actualBook = bookDao.getById(expectedBook.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

  }

  @Test
  @DisplayName("Получение списка книг")
  void getBooksListTest() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, new Author(EXISTING_ID_AUTHOR,EXISTING_NAME_AUTHOR),
        new Genre(EXISTING_ID_GENRE, EXISTING_NAME_GENRE));
    List<Book> actualBooks = bookDao.getAll();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Поиск книги по Id")
  void getBookByIdTest() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, new Author(EXISTING_ID_AUTHOR,EXISTING_NAME_AUTHOR),
        new Genre(EXISTING_ID_GENRE, EXISTING_NAME_GENRE));
    Book actualBook = bookDao.getById(expectedBook.getId());
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
