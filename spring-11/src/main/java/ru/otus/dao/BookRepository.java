package ru.otus.dao;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findAllByName(String name);

  @EntityGraph(type = EntityGraphType.FETCH, attributePaths = {"author", "genre"})
  List<Book> findAll();
}
