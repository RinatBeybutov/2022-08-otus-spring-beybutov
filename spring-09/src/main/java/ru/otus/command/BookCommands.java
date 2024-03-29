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

  @Autowired
  public BookCommands(BookService bookService) {
    this.bookService = bookService;
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
    return BookDtoConverter.bookDtosToStrings(books);
  }

  @ShellMethod(value = "get book by id", key = {"getBookById", "gi"})
  public String getBookByName(@ShellOption String name) {
    BookDto book = bookService.getBook(name);
    return BookDtoConverter.bookDtoToString(book);
  }

  @ShellMethod(value = "update book", key = {"updateBook", "u"})
  public String updateBook(@ShellOption String name,
      @ShellOption String author,
      @ShellOption String genre) {
    bookService.update(name, author, genre);
    return "книга изменена!";
  }

  @ShellMethod(value = "delete book", key = {"deleteBook", "d"})
  public String deleteBook(@ShellOption String name) {
    bookService.delete(name);
    return "книга удалена!";
  }
}
