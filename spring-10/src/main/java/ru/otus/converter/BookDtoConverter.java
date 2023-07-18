package ru.otus.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

@Component
public class BookDtoConverter {

  public BookDto convertBookToBookDto(Book book) {
    return new BookDto(book.getName(), book.getAuthor().getName(), book.getGenre().getName());
  }

  public String bookDtoToString(BookDto bookDto) {
    return String.format("%s - %s - %s", bookDto.getName(), bookDto.getAuthor(),
        bookDto.getGenre());
  }

  public String bookDtosToStrings(List<BookDto> books) {
    StringBuilder builder = new StringBuilder();
    books.forEach(book -> {
      builder.append(bookDtoToString(book) + "\n");
    });
    return builder.toString();
  }

  public List<BookDto> convertBooksToBookDtos(List<Book> books) {
    return books.stream()
        .map(book -> new BookDto(book.getName(), book.getAuthor().getName(),
            book.getGenre().getName()))
        .collect(Collectors.toList());
  }
}
