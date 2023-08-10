package ru.otus.service;

import ru.otus.domain.Genre;

public interface GenreService {

  Genre getOrCreateGenre(String name);
}
