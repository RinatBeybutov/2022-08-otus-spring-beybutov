package ru.otus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.BookRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

@DataJpaTest
@Import(BookServiceImpl.class)
@DisplayName("Сервис для работы с книгами")
public class BookServiceTest {

  private static final String EXISTED_BOOK_NAME = "War and peace";
  private static final String EXISTED_BOOK_AUTHOR_1 = "Ivan";
  private static final String EXISTED_BOOK_GENRE_1 = "detective";
  private static final String NEW_BOOK_NAME = "Bestseller";
  @Autowired
  private BookService bookService;

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private AuthorRepository authorRepository;

  @MockBean
  private GenreRepository genreRepository;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  @MockBean
  private BookDtoConverter bookDtoConverter;

  @Test
  @DisplayName("Вставка книги")
  public void createBookTest() {
    BookDto expectedBook = new BookDto(NEW_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<Book> mockBookEntity = Arrays.asList(
        new Book(3, NEW_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    Mockito.when(bookRepository.findAllByName(NEW_BOOK_NAME)).thenReturn(mockBookEntity);
    Mockito.when(bookDtoConverter.convertBooksToBookDtos(mockBookEntity)).thenReturn(Arrays.asList(expectedBook));
    bookService.save(expectedBook);
    BookDto actualBook = bookService.getBook(expectedBook.getName()).get(0);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Получение списка книг")
  public void getAllBooksTest() {
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    Book mockBook = new Book(3,EXISTED_BOOK_NAME,
        new Author(3, EXISTED_BOOK_AUTHOR_1, null),
        new Genre(3, EXISTED_BOOK_GENRE_1, null),
        null);
    Mockito.when(bookRepository.findAll()).thenReturn(List.of(mockBook));
    Mockito.when(bookDtoConverter.convertBookToBookDto(mockBook)).thenReturn(expectedBook);
    List<BookDto> actualBooks = bookService.getAllBooks();
    assertThat(actualBooks).containsExactlyInAnyOrder(expectedBook);
  }

  @Test
  @DisplayName("Получение книги")
  public void getBookByNameTest() {
    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_1, EXISTED_BOOK_GENRE_1);
    List<Book> mockBooks = Arrays.asList(
        new Book(3, EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    Mockito.when(bookRepository.findAllByName(EXISTED_BOOK_NAME)).thenReturn(mockBooks);
    Mockito.when(bookDtoConverter.convertBooksToBookDtos(mockBooks)).thenReturn(Arrays.asList(expectedBook));
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME).get(0);
    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  @DisplayName("Удаление книги")
  public void deleteBookTest() {
    List<Book> mockBook = Arrays.asList(
        new Book(3, EXISTED_BOOK_NAME, new Author(3, EXISTED_BOOK_AUTHOR_1, null),
            new Genre(3, EXISTED_BOOK_GENRE_1, null), null));
    Mockito.when(bookRepository.findAllByName(EXISTED_BOOK_NAME)).thenReturn(mockBook);
    assertThatCode(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .doesNotThrowAnyException();
    bookService.delete(EXISTED_BOOK_NAME);
    Mockito.when(bookRepository.findAllByName(EXISTED_BOOK_NAME)).thenThrow(new EmptyResultDataAccessException(9));
    assertThatThrownBy(() -> bookService.getBook(EXISTED_BOOK_NAME))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }
/*
  @Test
  @DisplayName("обновление книги")
  public void updateBookTest() {
    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(Arrays.asList(new Book(1,EXISTED_BOOK_NAME, new Author(1, EXISTED_BOOK_AUTHOR_1, null),
        new Genre(1, EXISTED_BOOK_GENRE_1, null),null)));

    BookDto expectedBook = new BookDto(EXISTED_BOOK_NAME, EXISTED_BOOK_AUTHOR_2, EXISTED_BOOK_GENRE_2);
    bookService.update(1, EXISTED_BOOK_NAME);

    Mockito.when(bookDao.getByName(EXISTED_BOOK_NAME)).thenReturn(Arrays.asList(new Book(1,EXISTED_BOOK_NAME, new Author(2, EXISTED_BOOK_AUTHOR_2, null),
        new Genre(2, EXISTED_BOOK_GENRE_2, null),null)));
    BookDto actualBook = bookService.getBook(EXISTED_BOOK_NAME).get(0);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }*/

}
