package ru.otus.service;

import java.util.List;
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
    Genre genreByName = null;
    try {
      List<Genre> genres = genreDao.getByName(name);
      if (genres.size() > 0) {
        genreByName = genres.get(0);
      }
    } catch (EmptyResultDataAccessException e) {
      genreByName = new Genre();
      genreByName.setName(name);
      genreByName.setBook(null);
      genreDao.insert(genreByName);
      genreByName = genreDao.getByName(name).get(0);
    }
    return genreByName;
  }
}
