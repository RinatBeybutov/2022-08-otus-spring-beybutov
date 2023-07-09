package ru.otus.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.converter.CommentConverter;
import ru.otus.dao.BookDaoJpa;
import ru.otus.dao.CommentDaoJpa;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentDaoJpa commentDao;

  private final BookDaoJpa bookDao;

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
  @Transactional
  public List<CommentDto> getByBookId(int bookId) {
    List<Comment> comments = commentDao.getAllByBookId(bookId);
    return CommentConverter.commentsToCommentDtos(comments);
  }

  @Override
  @Transactional
  public CommentDto getById(int commentId) {
    Comment comment = commentDao.getCommentById(commentId);
    return CommentConverter.commentToCommentDto(comment);
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
