package ru.otus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

@Repository
public class AuthorDaoJpa implements AuthorDao {

  @PersistenceContext
  private final EntityManager em;

  public AuthorDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public int count() {
    TypedQuery<Integer> query = em.createQuery("select count(*) from Author", Integer.class);
    return query.getSingleResult();
  }

  @Override
  public Author insert(Author author) {
    if (author.getId() <= 0) {
      em.persist(author);
      return author;
    } else {
      return em.merge(author);
    }
  }

  @Override
  public Author getById(long id) {
    return em.find(Author.class, id);
  }

  @Override
  public List<Author> getAll() {
    return em.createQuery("select a from Author a", Author.class)
        .getResultList();
  }

  @Override
  public void deleteById(long id) {
    Author author = getById(id);
    em.remove(author);
  }

  @Override
  public List<Author> getByName(String author) {
    TypedQuery<Author> query = em.createQuery("select a " +
            "from Author a " +
            "where a.name = :name",
        Author.class);
    query.setParameter("name", author);
    return query.getResultList();
  }
}
