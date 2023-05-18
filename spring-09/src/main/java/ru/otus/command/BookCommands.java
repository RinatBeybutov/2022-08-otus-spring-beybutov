package ru.otus.command;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
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
    return BookConverter.BookDtosToStrings(books);
  }

  @ShellMethod(value = "get book by id", key = {"getBookById", "gi"})
  public String getBookById(@ShellOption String name) {
    BookDto book = bookService.getBook(name);
    return BookConverter.BookDtoToString(book);
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

  private static class BookConverter {
    public static String BookDtoToString(BookDto bookDto) {
      return String.format("%s - %s - %s", bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
    }

    public static String BookDtosToStrings(List<BookDto> books ){
      StringBuilder builder = new StringBuilder();
      books.forEach(book -> {
        builder.append(BookDtoToString(book) + "\n");
      });
      return builder.toString();
    }
  }
}
