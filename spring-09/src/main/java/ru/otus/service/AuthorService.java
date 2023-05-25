package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorDao authorDao;

  public Author getOrCreatePerson(String name) {
    Author personByName;
    try {
      personByName = authorDao.getByName(name);
    } catch (EmptyResultDataAccessException e) {
      personByName = new Author(-1, name);
      authorDao.insert(personByName);
      personByName = authorDao.getByName(name);
    }
    return personByName;
  }
}
