package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Book;

public interface BookDao {

  List<Book> getAll();

  Book insert(Book book);

  Book getById(long id);

  void deleteById(long id);

  int count();

  List<Book> getByName(String name);

  void updateByName(Book book);
}
