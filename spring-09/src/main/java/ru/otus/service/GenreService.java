package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreDao genreDao;

  public Genre getOrCreateGenre(String name) {
    Genre genreByName;
    try {
      genreByName = genreDao.getByName(name);
    } catch (EmptyResultDataAccessException e) {
      genreByName = new Genre(-1, name);
      genreDao.insert(genreByName);
      genreByName = genreDao.getByName(name);
    }
    return genreByName;
  }
}
