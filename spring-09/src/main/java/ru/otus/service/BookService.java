package ru.otus.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.domain.Genre;
import ru.otus.domain.Author;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookDao bookDao;
  private final AuthorDao personDao;
  private final GenreDao genreDao;

  public void save(BookDto book) {
    Genre genre = getOrCreateGenre(book.getGenre());
    Author person = getOrCreatePerson(book.getAuthor());
    bookDao.insert(new Book(-1, book.getName(),
        person, genre));
  }

  public List<BookDto> getAllBooks() {
    List<Book> books = bookDao.getAll();
    return books.stream().map(this::convertBookDtoToBook)
        .collect(Collectors.toList());
  }

  public BookDto getBook(String name) {
    Book book = bookDao.getByName(name);
    return convertBookDtoToBook(book);
  }

  public void update(String name, String author, String genre) {
    Genre genreDto = getOrCreateGenre(genre);
    Author person = getOrCreatePerson(author);
    Book bookDto = bookDao.getByName(name);
    bookDao.updateByName(new Book(bookDto.getId(), name,
        person, genreDto));
  }

  public void delete(String name) {
    Book bookDto = bookDao.getByName(name);
    if (bookDto != null) {
      bookDao.deleteById(bookDto.getId());
    } else {
      System.out.println("Книга с таким названием отсутствует!");
    }
  }

  private Genre getOrCreateGenre(String name) {
    Genre genreByName;
    try {
      genreByName = genreDao.getByName(name);
    } catch (EmptyResultDataAccessException e) {
      genreByName = new Genre(-1, name);
      genreDao.insert(genreByName);
      genreByName = genreDao.getByName(name);
    }
    return genreByName;
  }

  private Author getOrCreatePerson(String name) {
    Author personByName;
    try {
      personByName = personDao.getByName(name);
    } catch (EmptyResultDataAccessException e) {
      personByName = new Author(-1, name);
      personDao.insert(personByName);
      personByName = personDao.getByName(name);
    }
    return personByName;
  }

  private BookDto convertBookDtoToBook(Book book) {
    return new BookDto(book.getName(), book.getAuthor().getName(), book.getGenre().getName());
  }
}
