package ru.otus.dao;

import java.util.List;
import ru.otus.dto.GenreDto;

public interface GenreDao {

  List<GenreDto> getAll();

  void insert(GenreDto bookDto);

  GenreDto getById(long id);

  void deleteById(long id);

  GenreDto getByName(String name);
}
