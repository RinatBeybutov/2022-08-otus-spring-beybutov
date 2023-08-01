package ru.otus.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.BookDtoConverter;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookDao bookDao;

  private final AuthorService authorService;

  private final GenreService genreService;

  private final BookDtoConverter bookDtoConverter;

  @Transactional
  public void save(BookDto book) {
    Genre genre = genreService.getOrCreateGenre(book.getGenre());
    Author person = authorService.getOrCreatePerson(book.getAuthor());
    bookDao.insert(new Book(0, book.getName(),
        person, genre, null));
  }

  @Transactional(readOnly = true)
  public List<BookDto> getAllBooks() {
    List<Book> books = bookDao.getAll();
    return books.stream().map(bookDtoConverter::convertBookToBookDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<BookDto> getBook(String name) {
    List<Book> books = bookDao.getByName(name);
    return bookDtoConverter.convertBooksToBookDtos(books);
  }

  @Transactional
  public void update(long id, String name) {
    Book book = bookDao.getById(id);
    if (book != null) {
      bookDao.updateNameById(new Book(book.getId(), name,
          null, null, null));
    }
  }

  @Transactional
  public void delete(String name) {
    List<Book> books = bookDao.getByName(name);
    if (books.size() > 0) {
      bookDao.deleteById(books.get(0).getId());
    }
  }
}
