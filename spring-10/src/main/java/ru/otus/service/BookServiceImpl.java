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

  @Transactional
  public void save(BookDto book) {
    Genre genre = genreService.getOrCreateGenre(book.getGenre());
    Author person = authorService.getOrCreatePerson(book.getAuthor());
    bookDao.insert(new Book(-1, book.getName(),
        person, genre, null));
  }

  @Transactional(readOnly = true)
  public List<BookDto> getAllBooks() {
    List<Book> books = bookDao.getAll();
    return books.stream().map(BookDtoConverter::convertBookToBookDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<BookDto> getBook(String name) {
    List<Book> books = bookDao.getByName(name);
    return BookDtoConverter.convertBooksToBookDtos(books);
  }

  @Transactional
  public void update(long id, String name) {
    List<Book> books = bookDao.getByName(name);
    if(books.size() > 0) {
      Book book = books.get(0);
      bookDao.updateByName(new Book(book.getId(), name,
          null, null, book.getComments()));
    }
  }

  @Transactional
  public void delete(String name) {
    List<Book> books = bookDao.getByName(name);
    if(books.size() > 0) {
      bookDao.deleteById(books.get(0).getId());
    }
  }
}
