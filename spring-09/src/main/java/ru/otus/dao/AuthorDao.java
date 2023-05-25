package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Author;

public interface AuthorDao {
  int count();

  void insert(Author author);

  Author getById(long id);

  List<Author> getAll();

  void deleteById(long id);

  Author getByName(String author);
}
