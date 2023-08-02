package ru.otus.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findAllByName(String name);
}
