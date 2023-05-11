package ru.otus.dao;

import java.util.List;
import ru.otus.dto.PersonDto;
import ru.otus.dto.BookDto;

public interface BookDao {

  List<BookDto> getAll();

  void insert(BookDto bookDto);

  BookDto getById(long id);

  void deleteById(long id);

  int count();

  BookDto getByName(String name);

  void updateByName(BookDto bookDto);
}
