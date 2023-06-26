package ru.otus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

@Repository
public class CommentDaoJpa implements CommentDao {

  @PersistenceContext
  private final EntityManager em;

  public CommentDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public Comment insert(Comment comment) {
    if (comment.getId() <= 0) {
      em.persist(comment);
      return comment;
    } else {
      return em.merge(comment);
    }
  }

  @Override
  public List<Comment> getAllByBookId(long bookId) {
    TypedQuery<Comment> query = em.createQuery("select c " +
            "from Comment c " +
            "where c.book.id = :id",
        Comment.class);
    query.setParameter("id", bookId);
    return query.getResultList();
  }

  @Override
  public Comment getCommentById(long commentId) {
    return em.find(Comment.class, commentId);
  }

  @Override
  public void updateById(String text, long commentId) {
    Query query = em.createQuery("update Comment c " +
        "set c.text = :text " +
        "where c.id = :id");
    query.setParameter("text", text);
    query.setParameter("id", commentId);
    query.executeUpdate();
  }

  @Override
  public void deleteById(long id) {
    Query query = em.createQuery("delete " +
        "from Comment c " +
        "where c.id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }
}
