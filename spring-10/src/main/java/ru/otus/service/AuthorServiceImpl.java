package ru.otus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorDao authorDao;

  @Transactional
  public Author getOrCreatePerson(String name) {
    Author personByName = null;
    List<Author> authors = authorDao.getByName(name);
    if (authors.size() > 0) {
      personByName = authors.get(0);
    }
    if (authors.size() == 0) {
      personByName = new Author();
      personByName.setName(name);
      personByName.setBooks(null);
      authorDao.insert(personByName);
      personByName = authorDao.getByName(name).get(0);
    }
    return personByName;
  }
}
