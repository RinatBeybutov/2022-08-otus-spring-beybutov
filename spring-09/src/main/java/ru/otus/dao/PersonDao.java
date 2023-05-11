package ru.otus.dao;

import java.util.List;
import ru.otus.dto.PersonDto;

public interface PersonDao {
  int count();

  void insert(PersonDto personDto);

  PersonDto getById(long id);

  List<PersonDto> getAll();

  void deleteById(long id);

  PersonDto getByName(String author);
}
