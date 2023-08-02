package ru.otus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.domain.Genre;

@DisplayName("Репозиторий для работы с жанрами")
@DataJpaTest
public class GenreRepositoryTest {

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private EntityManager em;

  @Test
  @DisplayName("Нахождение жанра по имени")
  public void findAllByNameTest() {
    TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :genreName",
        Genre.class);
    query.setParameter("genreName", "detective");
    List<Genre> expected = query.getResultList();
    List<Genre> actual = genreRepository.findAllByName("detective");
    assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
  }
}
