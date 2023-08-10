package ru.otus.service;

import java.util.List;
import ru.otus.dto.CommentDto;

public interface CommentService {

  void save(CommentDto commentDto);

  List<CommentDto> getByBookId(int bookId);

  CommentDto getById(int commentId);

  void update(int commentId, String text);

  void delete(int commentId);

}
