package ru.otus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorRepository;
import ru.otus.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Transactional
  public Author getOrCreatePerson(String name) {
    Author personByName = null;
    List<Author> authors = authorRepository.findAllByName(name);
    if (authors.size() > 0) {
      personByName = authors.get(0);
    }
    if (authors.size() == 0) {
      personByName = new Author();
      personByName.setName(name);
      personByName.setBooks(null);
      personByName = authorRepository.save(personByName);
    }
    return personByName;
  }
}
