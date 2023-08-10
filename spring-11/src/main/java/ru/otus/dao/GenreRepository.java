package ru.otus.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

  List<Genre> findAllByName(String name);
}
