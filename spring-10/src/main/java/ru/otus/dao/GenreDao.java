package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

public interface GenreDao {

  List<Genre> getAll();

  Genre insert(Genre genre);

  Genre getById(long id);

  void deleteById(long id);

  List<Genre> getByName(String name);
}
