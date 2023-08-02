package ru.otus.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.BookDtoConverter;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  private final AuthorService authorService;

  private final GenreService genreService;

  private final BookDtoConverter bookDtoConverter;

  @Transactional
  public void save(BookDto book) {
    Genre genre = genreService.getOrCreateGenre(book.getGenre());
    Author person = authorService.getOrCreatePerson(book.getAuthor());
    bookRepository.save(new Book(0, book.getName(),
        person, genre, null));
  }

  @Transactional(readOnly = true)
  public List<BookDto> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    return books.stream().map(bookDtoConverter::convertBookToBookDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<BookDto> getBook(String name) {
    List<Book> books = bookRepository.findAllByName(name);
    return bookDtoConverter.convertBooksToBookDtos(books);
  }

  @Transactional
  public void update(long id, String name) {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      book.setName(name);
      bookRepository.save(book);
    }
  }

  @Transactional
  public void delete(String name) {
    List<Book> books = bookRepository.findAllByName(name);
    if (books.size() > 0) {
      bookRepository.deleteAll(books);
    }
  }
}
