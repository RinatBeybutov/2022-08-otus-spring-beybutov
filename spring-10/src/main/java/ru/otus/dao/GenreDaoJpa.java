package ru.otus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

@Repository
public class GenreDaoJpa implements GenreDao {

  @PersistenceContext
  private final EntityManager em;

  public GenreDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<Genre> getAll() {
    return em.createQuery("select g from Genre g", Genre.class)
        .getResultList();
  }

  @Override
  public Genre insert(Genre genre) {
    if (genre.getId() <= 0) {
      em.persist(genre);
      return genre;
    } else {
      return em.merge(genre);
    }
  }

  @Override
  public Genre getById(long id) {
    return em.find(Genre.class, id);
  }

  @Override
  public void deleteById(long id) {
    Genre genre = getById(id);
    em.remove(genre);
  }

  @Override
  public List<Genre> getByName(String name) {
    TypedQuery<Genre> query = em.createQuery("select g " +
            "from Genre g " +
            "where g.name = :name",
        Genre.class);
    query.setParameter("name", name);
    return query.getResultList();
  }
}
