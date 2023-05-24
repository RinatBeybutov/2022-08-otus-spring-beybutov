package ru.otus.service;

import java.util.List;
import ru.otus.dto.BookDto;

public interface BookService {

  void save(BookDto book);

  List<BookDto> getAllBooks();

  BookDto getBook(String name);

  void update(String name, String author, String genre);

  void delete(String name);


}
