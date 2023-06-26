package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Author;
import ru.otus.domain.Book;

public interface AuthorDao {
  int count();

  Author insert(Author author);

  Author getById(long id);

  List<Author> getAll();

  void deleteById(long id);

  List<Author> getByName(String author);
}
