package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Comment;

public interface CommentDao {

  Comment insert(Comment comment);

  List<Comment> getAllByBookId(long bookId);

  Comment getCommentById(long commentId);

  void updateById(String text, long commentId);

  void deleteById(long id);

}
