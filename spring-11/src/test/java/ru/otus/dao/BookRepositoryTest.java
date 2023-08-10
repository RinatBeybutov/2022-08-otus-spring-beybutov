package ru.otus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import ru.otus.domain.Book;

@DisplayName("Репозиторий для работы с книгами")
@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private EntityManager em;

  private final String EXISTING_BOOK_NAME = "War and peace";

  @Test
  public void getAllByNameTest() {
    TypedQuery<Book> query = em.createQuery("select b " +
            "from Book b " +
            "where b.name = :name",
        Book.class);
    query.setParameter("name", EXISTING_BOOK_NAME);
    List<Book> expectedBooks = query.getResultList();
    List<Book> actualBooks = bookRepository.findAllByName(EXISTING_BOOK_NAME);
    assertThat(expectedBooks).hasSize(actualBooks.size());
  }
}
