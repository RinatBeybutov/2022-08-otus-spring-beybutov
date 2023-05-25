package ru.otus.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

  public void save(BookDto book) {
    Genre genre = genreService.getOrCreateGenre(book.getGenre());
    Author person = authorService.getOrCreatePerson(book.getAuthor());
    bookDao.insert(new Book(-1, book.getName(),
        person, genre));
  }

  public List<BookDto> getAllBooks() {
    List<Book> books = bookDao.getAll();
    return books.stream().map(BookDtoConverter::convertBookDtoToBook)
        .collect(Collectors.toList());
  }

  public BookDto getBook(String name) {
    Book book = bookDao.getByName(name);
    return BookDtoConverter.convertBookDtoToBook(book);
  }

  public void update(String name, String author, String genre) {
    Genre genreDto = genreService.getOrCreateGenre(genre);
    Author person = authorService.getOrCreatePerson(author);
    Book bookDto = bookDao.getByName(name);
    bookDao.updateByName(new Book(bookDto.getId(), name,
        person, genreDto));
  }

  public void delete(String name) {
    Book bookDto = bookDao.getByName(name);
    bookDao.deleteById(bookDto.getId());
  }
}
