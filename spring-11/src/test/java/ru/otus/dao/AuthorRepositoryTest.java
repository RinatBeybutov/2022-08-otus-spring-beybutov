package ru.otus.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.domain.Author;

@DisplayName("Репозиторий для работы с авторами")
@DataJpaTest
public class AuthorRepositoryTest {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private EntityManager em;

  @Test
  @DisplayName("Получение автора по имени")
  public void findAllByNameTest() {
    List<Author> actualAuthor = authorRepository.findAllByName("Ivan");
    TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :authorName",
        Author.class);
    query.setParameter("authorName", "Ivan");
    List<Author> expectedAuthor = query.getResultList();
    assertThat(expectedAuthor).usingRecursiveComparison().isEqualTo(actualAuthor);
  }
}
