package ru.otus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreDao genreDao;

  @Transactional
  public Genre getOrCreateGenre(String name) {
    Genre genreByName = null;
    List<Genre> genres = genreDao.getByName(name);
    if (genres.size() > 0) {
      genreByName = genres.get(0);
    }
    if (genres.size() == 0) {
      genreByName = new Genre();
      genreByName.setName(name);
      genreByName.setBook(null);
      genreDao.insert(genreByName);
      genreByName = genreDao.getByName(name).get(0);
    }
    return genreByName;
  }
}
