package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Genre;

public interface GenreDao {

  List<Genre> getAll();

  void insert(Genre genre);

  Genre getById(long id);

  void deleteById(long id);

  Genre getByName(String name);
}
