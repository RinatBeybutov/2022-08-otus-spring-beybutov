package ru.otus.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByBookId(long bookId);
}
