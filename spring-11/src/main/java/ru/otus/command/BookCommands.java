package ru.otus.command;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.converter.BookDtoConverter;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

@ShellComponent
public class BookCommands {

  private final BookService bookService;

  private final BookDtoConverter bookDtoConverter;

  @Autowired
  public BookCommands(BookService bookService, BookDtoConverter bookDtoConverter) {
    this.bookService = bookService;
    this.bookDtoConverter = bookDtoConverter;
  }

  @ShellMethod(value = "insert book command", key = {"insertBook", "i"})
  public String insertBook(@ShellOption String name,
      @ShellOption String author,
      @ShellOption String genre) {
    bookService.save(new BookDto(name, author, genre));
    return "книга сохранена!";
  }

  @ShellMethod(value = "get all books", key = {"getAllBooks", "ga"})
  public String getAllBooks() {
    List<BookDto> books = bookService.getAllBooks();
    return bookDtoConverter.bookDtosToStrings(books);
  }

  @ShellMethod(value = "get book by name", key = {"getBookByName", "gn"})
  public String getBookByName(@ShellOption String name) {
    List<BookDto> bookDtos = bookService.getBook(name);
    return bookDtoConverter.bookDtosToStrings(bookDtos);
  }

  @ShellMethod(value = "update book", key = {"updateBook", "u"})
  public String updateBook(@ShellOption long id,
      @ShellOption String name) {
    bookService.update(id, name);
    return "книга изменена!";
  }

  @ShellMethod(value = "delete book", key = {"deleteBook", "d"})
  public String deleteBook(@ShellOption String name) {
    bookService.delete(name);
    return "книга удалена!";
  }
}
