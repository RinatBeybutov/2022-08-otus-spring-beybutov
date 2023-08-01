package ru.otus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.CommentConverter;
import ru.otus.dao.BookDao;
import ru.otus.dao.CommentDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentDao commentDao;

  private final BookDao bookDao;

  private final CommentConverter commentConverter;

  @Override
  @Transactional
  public void save(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setText(commentDto.getText());
    Book book = bookDao.getById(commentDto.getBookId());
    comment.setBook(book);
    commentDao.insert(comment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommentDto> getByBookId(int bookId) {
    List<Comment> comments = commentDao.getAllByBookId(bookId);
    return commentConverter.commentsToCommentDtos(comments);
  }

  @Override
  @Transactional(readOnly = true)
  public CommentDto getById(int commentId) {
    Comment comment = commentDao.getCommentById(commentId);
    return commentConverter.commentToCommentDto(comment);
  }

  @Override
  @Transactional
  public void update(int commentId, String text) {
    commentDao.updateById(text, commentId);
  }

  @Override
  @Transactional
  public void delete(int commentId) {
    commentDao.deleteById(commentId);
  }
}
