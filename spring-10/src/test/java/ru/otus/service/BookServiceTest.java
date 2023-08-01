package ru.otus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.converter.BookDtoConverter;
import ru.otus.dao.AuthorDaoJpa;
import ru.otus.dao.BookDaoJpa;
import ru.otus.dao.GenreDaoJpa;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

@DataJpaTest
@Import(BookServiceImpl.class)
public class BookServiceTest {

  private static final String EXISTED_BOOK_NAME = "War and peace";
  private static final String EXISTED_BOOK_AUTHOR_1 = "Ivan";
  private static final String EXISTED_BOOK_AUTHOR_2 = "Mary";
  private static final String EXISTED_BOOK_GENRE_1 = "detective";
  private static final String EXISTED_BOOK_GENRE_2 = "history";
  private static final String NEW_BOOK_NAME = "Bestseller";
  @Autowired
  private BookService bookService;

  @MockBean
  private BookDaoJpa bookDao;

  @MockBean
  private AuthorDaoJpa authorDao;

  @MockBean
  private GenreDaoJpa genreDao;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  @MockBean
  private BookDtoConverter bookDtoConverter;

  @Test
  @DisplayName("Создание книги")
  public void createBookTest() {
    BookDto expectedBook = new BookDto(NEW_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<Book> mockBooks = Arrays.asList(
        new Book(3, NEW_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    List<Genre> mockGenre = Arrays.asList(new Genre(3, EXISTED_BOOK_GENRE_1, null));
    List<Author> mockAuthor = Arrays.asList(new Author(3, EXISTED_BOOK_AUTHOR_1, null));
    Mockito.when(bookDao.getByName(NEW_BOOK_NAME)).thenReturn(mockBooks);
    Mockito.when(genreDao.getByName(EXISTED_BOOK_GENRE_1)).thenReturn(mockGenre);
    Mockito.when(authorDao.getByName(EXISTED_BOOK_AUTHOR_1)).thenReturn(mockAuthor);
    List<BookDto> mockBookDtos = Arrays.asList(new BookDto(NEW_BOOK_NAME, EXISTED_BOOK_AUTHOR_1,EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDtoConverter.convertBooksToBookDtos(mockBooks)).thenReturn(mockBookDtos);
    bookService.save(expectedBook);
    BookDto actualBook = bookService.getBook(expectedBook.getName()).get(0);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Получение списка книг")
  public void getAllBooksTest() {
    Book mockBook = new Book(3,EXISTED_BOOK_NAME,
        new Author(3, EXISTED_BOOK_AUTHOR_1, null),
        new Genre(3, EXISTED_BOOK_GENRE_1, null),
        null);
    BookDto mockBookDto = new BookDto(EXISTED_BOOK_NAME,EXISTED_BOOK_AUTHOR_1,EXISTED_BOOK_GENRE_1);
    Mockito.when(bookDao.getAll()).thenReturn(List.of(mockBook));
    Mockito.when(bookDtoConverter.convertBookToBookDto(mockBook)).thenReturn(mockBookDto);
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<BookDto> actualBooks = bookService.getAllBooks();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Получение книги")
  public void getBookByNameTest() {
    List<Book> mockBooks = Arrays.asList(
        new Book(3, EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    List<BookDto> mockBookDtos = Arrays.asList(new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1,EXISTED_BOOK_GENRE_1));
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(mockBooks);
    Mockito.when(bookDtoConverter.convertBooksToBookDtos(mockBooks)).thenReturn(mockBookDtos);
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME).get(0);
    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Удаление книги")
  public void deleteBookTest() {
    List<Book> mockBook = Arrays.asList(
        new Book(3, EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(mockBook);
    assertThatCode(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .doesNotThrowAnyException();
    bookService.delete(EXISTED_BOOK_NAME);
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenThrow(new EmptyResultDataAccessException(9));
    assertThatThrownBy(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @Test
  @DisplayName("обновление книги")
  public void updateBookTest() {
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(Arrays.asList(new Book(1,EXISTED_BOOK_NAME, new Author(1, EXISTED_BOOK_AUTHOR_1, null),
        new Genre(1, EXISTED_BOOK_GENRE_1, null),null)));

    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);
    bookService.update(1, EXISTED_BOOK_NAME);
    List<Book> mockBooks = Arrays.asList(
        new Book(1, EXISTED_BOOK_NAME, new Author(2, EXISTED_BOOK_AUTHOR_2, null),
            new Genre(2, EXISTED_BOOK_GENRE_2, null), null));
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(mockBooks);
    List<BookDto> mockBookDtos = Arrays.asList(new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2,EXISTED_BOOK_GENRE_2));
    Mockito.when(bookDtoConverter.convertBooksToBookDtos(mockBooks)).thenReturn(mockBookDtos);
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME).get(0);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

}
