package ru.otus.service;

import java.util.List;
import ru.otus.dto.BookDto;

public interface BookService {

  void save(BookDto book);

  List<BookDto> getAllBooks();

  List<BookDto> getBook(String name);

  void update(long id, String name);

  void delete(String name);


}
