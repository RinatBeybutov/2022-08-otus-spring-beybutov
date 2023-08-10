package ru.otus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

@DisplayName("Репозиторий для работы с комментами")
@DataJpaTest
public class CommentRepositoryTest {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private EntityManager em;

  private final long EXISTED_BOOK_ID = 1L;

  @Test
  @DisplayName("Получение всех комментариев к книге")
  public void findAllByBookIdTest() {
    TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId",
        Comment.class);
    query.setParameter("bookId", EXISTED_BOOK_ID);
    List<Comment> expectedComments = query.getResultList();
    List<Comment> actualComments = commentRepository.findAllByBookId(EXISTED_BOOK_ID);
    assertThat(expectedComments).hasSize(actualComments.size());
  }


}
