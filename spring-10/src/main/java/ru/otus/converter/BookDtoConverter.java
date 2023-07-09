package ru.otus.converter;

import java.util.List;
import java.util.stream.Collectors;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

public class BookDtoConverter {

  public static BookDto convertBookToBookDto(Book book) {
    return new BookDto(book.getName(), book.getAuthor().getName(), book.getGenre().getName());
  }

  public static String bookDtoToString(BookDto bookDto) {
    return String.format("%s - %s - %s", bookDto.getName(), bookDto.getAuthor(),
        bookDto.getGenre());
  }

  public static String bookDtosToStrings(List<BookDto> books) {
    StringBuilder builder = new StringBuilder();
    books.forEach(book -> {
      builder.append(bookDtoToString(book) + "\n");
    });
    return builder.toString();
  }

  public static List<BookDto> convertBooksToBookDtos(List<Book> books) {
    return books.stream()
        .map(book -> new BookDto(book.getName(), book.getAuthor().getName(),
            book.getGenre().getName()))
        .collect(Collectors.toList());
  }
}
