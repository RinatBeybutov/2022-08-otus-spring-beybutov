package ru.otus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

@Repository
public class BookDaoJpa implements BookDao {

  @PersistenceContext
  private final EntityManager em;

  public BookDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<Book> getAll() {
    return em.createQuery("select b from Book b join fetch b.genre join fetch b.author", Book.class)
        .getResultList();
  }

  @Override
  public Book insert(Book book) {
    if (book.getId() < 0) {
      em.persist(book);
      return book;
    } else {
      return em.merge(book);
    }
  }

  @Override
  public Book getById(long id) {
    return em.find(Book.class, id);
  }

  @Override
  public void deleteById(long id) {
    Book book = getById(id);
    em.remove(book);
  }

  @Override
  public int count() {
    TypedQuery<Long> query = em.createQuery("select count(*) from Book", Long.class);
    return query.getSingleResult().intValue();
  }

  @Override
  public List<Book> getByName(String name) {
    TypedQuery<Book> query = em.createQuery("select b " +
            "from Book b " +
            "where b.name = :name",
        Book.class);
    query.setParameter("name", name);
    return query.getResultList();
  }

  @Override
  public void updateNameById(Book book) {
    Query query = em.createQuery("update Book b " +
        "set b.name = :name " +
        "where b.id = :id");
    query.setParameter("name", book.getName());
    query.setParameter("id", book.getId());
    query.executeUpdate();
  }
}
