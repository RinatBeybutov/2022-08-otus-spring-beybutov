package ru.otus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@DisplayName("Репозиторий Jpa для работы с книгами")
@DataJpaTest
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {

  private static final long EXISTING_BOOK_ID = 1;
  private static final long EXISTING_ID_AUTHOR = 1;
  private static final String EXISTING_NAME_AUTHOR ="Ivan";
  private static final long EXISTING_ID_GENRE = 1;
  private static final String EXISTING_NAME_GENRE = "detective";
  private static final int EXPECTED_BOOKS_COUNT = 1;
  private static final int EMPTY_LIST_SIZE = 0;
  private static final String NEW_BOOK_NAME = "New book name";

  @Autowired
  private BookDaoJpa bookDao;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Обновление книги")
  void updateBookTest() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, NEW_BOOK_NAME, null,
        null, null);
    bookDao.updateById(expectedBook);
    Book actualBook = bookDao.getByName(NEW_BOOK_NAME).get(0);
    assertThat(actualBook.getName()).isEqualTo(NEW_BOOK_NAME);
  }

  @Test
  @DisplayName("Вставка книги")
  void insertBookTest() {
    int countBeforeInsert = bookDao.count();
    assertThat(countBeforeInsert).isEqualTo(EXPECTED_BOOKS_COUNT);

    Book expectedBook = new Book(2, NEW_BOOK_NAME, null,
       null , null);
    List<Book> books = Arrays.asList(expectedBook);
    Author author = new Author(EXISTING_ID_AUTHOR, EXISTING_NAME_AUTHOR, books);
    Genre genre = new Genre(EXISTING_ID_GENRE, EXISTING_NAME_GENRE, books);
    expectedBook.setAuthor(author);
    expectedBook.setGenre(genre);
    bookDao.insert(expectedBook);

    Book actualBook = bookDao.getByName(expectedBook.getName()).get(0);
    assertThat(actualBook.getName()).isEqualTo(expectedBook.getName());
    assertThat(actualBook.getAuthor().getName()).isEqualTo(expectedBook.getAuthor().getName());
    assertThat(actualBook.getGenre().getName()).isEqualTo(expectedBook.getGenre().getName());
  }

  @Test
  @DisplayName("Получение списка книг")
  void getBooksListTest() {
    List<Book> actualBooks = bookDao.getAll();
    assertThat(actualBooks).hasSize(EXPECTED_BOOKS_COUNT);
  }

  @Test
  @DisplayName("Поиск книги по Id")
  void getBookByIdTest() {
    val optionalActualBook = bookDao.getById(EXISTING_BOOK_ID);
    val expectedStudent = em.find(Book.class, EXISTING_BOOK_ID);
    assertThat(optionalActualBook).usingRecursiveComparison()
        .isEqualTo(expectedStudent);
  }

  @Test
  @DisplayName("Удаление книги")
  void deleteBookByIdTest() {
    Book notNullBook = bookDao.getById(EXISTING_BOOK_ID);
    assertThat(notNullBook).isNotNull();
    bookDao.deleteById(EXISTING_BOOK_ID);
    List<Book> allBooks = bookDao.getAll();
    assertThat(allBooks).hasSize(EMPTY_LIST_SIZE);
  }
}
