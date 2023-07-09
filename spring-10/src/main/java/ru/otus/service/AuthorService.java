package ru.otus.service;

import java.util.List;
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
    Author personByName = null;
    try {
      List<Author> authors = authorDao.getByName(name);
      if (authors.size() > 0) {
        personByName = authors.get(0);
      }
    } catch (EmptyResultDataAccessException e) {
      personByName = new Author();
      personByName.setName(name);
      personByName.setBooks(null);
      authorDao.insert(personByName);
      personByName = authorDao.getByName(name).get(0);
    }
    return personByName;
  }
}
