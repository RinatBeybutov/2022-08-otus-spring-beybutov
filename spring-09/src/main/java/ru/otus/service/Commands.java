package ru.otus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Book;

@ShellComponent
public class Commands {

  private BookService bookService;

  @Autowired
  public Commands(BookService bookService) {
    this.bookService = bookService;
  }

  @ShellMethod(value = "insert book command", key = {"insertBook", "i"})
  public String insertBook(@ShellOption String name,
      @ShellOption String author,
      @ShellOption String genre) {
    bookService.save(new Book(name, author, genre));
    return "книга сохранена!";
  }

  @ShellMethod(value = "get all books", key = {"getAllBooks", "ga"})
  public String getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    StringBuilder builder = new StringBuilder();
    books.forEach(book -> {
      builder.append(book.getName() + "-"+ "\t");
      builder.append(book.getGenre() + "-"+ "\t");
      builder.append(book.getAuthor() + "-"+ "\n");
    });
    return builder.toString();
  }

  @ShellMethod(value = "get book by id", key = {"getBookById", "gi"})
  public String getBookById(@ShellOption String name) {
    Book book = bookService.getBook(name);
    return book.getName() + "\t" + book.getAuthor() + "\t" + book.getGenre();
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
