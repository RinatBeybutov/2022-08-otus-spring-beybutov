package ru.otus.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.dao.IdPointer;
import ru.otus.dao.PersonDao;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.dto.GenreDto;
import ru.otus.dto.PersonDto;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookDao bookDao;
  private final PersonDao personDao;
  private final GenreDao genreDao;

  public void save(Book book) {
    GenreDto genre = getOrCreateGenre(book.getGenre());
    PersonDto person = getOrCreatePerson(book.getAuthor());
    bookDao.insert(new BookDto(IdPointer.getNewBookId(), book.getName(),
        person.getId(), genre.getId()));
  }

  public List<Book> getAllBooks() {
    List<BookDto> bookDtos = bookDao.getAll();
    return bookDtos.stream().map(this::convertBookDtoToBook)
        .collect(Collectors.toList());
  }

  public Book getBook(String name) {
    BookDto bookDto = bookDao.getByName(name);
    return convertBookDtoToBook(bookDto);
  }

  public void update(String name, String author, String genre) {
    GenreDto genreDto = getOrCreateGenre(genre);
    PersonDto person = getOrCreatePerson(author);
    BookDto bookDto = bookDao.getByName(name);
    bookDao.updateByName(new BookDto(bookDto.getId(), name,
        person.getId(), genreDto.getId()));
  }

  public void delete(String name) {
    BookDto bookDto = bookDao.getByName(name);
    if (bookDto != null) {
      bookDao.deleteById(bookDto.getId());
    } else {
      System.out.println("Книга с таким названием отсутствует!");
    }
  }

  private GenreDto getOrCreateGenre(String name) {
    GenreDto genreByName = genreDao.getByName(name);
    if (genreByName == null) {
      genreByName = new GenreDto(IdPointer.getNewGenreId(), name);
      genreDao.insert(genreByName);
    }
    return genreByName;
  }

  private PersonDto getOrCreatePerson(String name) {
    PersonDto personByName = personDao.getByName(name);
    if (personByName == null) {
      personByName = new PersonDto(IdPointer.getNewPersonId(), name);
      personDao.insert(personByName);
    }
    return personByName;
  }

  private Book convertBookDtoToBook(BookDto bookDto) {
    String authorName = personDao.getById(bookDto.getId_author()).getName();
    String genreName = genreDao.getById(bookDto.getId_genre()).getName();
    return new Book(bookDto.getName(), authorName, genreName);
  }
}
