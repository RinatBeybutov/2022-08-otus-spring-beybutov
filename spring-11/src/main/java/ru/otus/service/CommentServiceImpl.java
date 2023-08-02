package ru.otus.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.CommentConverter;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final BookRepository bookRepository;

  private final CommentConverter commentConverter;

  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public void save(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setText(commentDto.getText());
    Optional<Book> optionalBook = bookRepository.findById(commentDto.getBookId());
    if (optionalBook.isPresent()) {
      comment.setBook(optionalBook.get());
      commentRepository.save(comment);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommentDto> getByBookId(int bookId) {
    List<Comment> comments = commentRepository.findAllByBookId(bookId);
    return commentConverter.commentsToCommentDtos(comments);
  }

  @Override
  @Transactional(readOnly = true)
  public CommentDto getById(int commentId) {
    Comment comment = commentRepository.findById((long) commentId).get();
    return commentConverter.commentToCommentDto(comment);
  }

  @Override
  @Transactional
  public void update(int commentId, String text) {
    Comment comment = commentRepository.findById((long) commentId).get();
    comment.setText(text);
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void delete(int commentId) {
    commentRepository.deleteById((long) commentId);
  }
}
