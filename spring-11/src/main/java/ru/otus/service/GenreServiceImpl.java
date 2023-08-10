package ru.otus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Transactional
  public Genre getOrCreateGenre(String name) {
    Genre genreByName = null;
    List<Genre> genres = genreRepository.findAllByName(name);
    if (genres.size() > 0) {
      genreByName = genres.get(0);
    }
    if (genres.size() == 0) {
      genreByName = new Genre();
      genreByName.setName(name);
      genreByName.setBook(null);
      genreByName = genreRepository.save(genreByName);
    }
    return genreByName;
  }
}
