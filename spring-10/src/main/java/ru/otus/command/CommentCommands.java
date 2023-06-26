package ru.otus.command;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.converter.BookDtoConverter;
import ru.otus.converter.CommentConverter;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.service.CommentService;

@ShellComponent
public class CommentCommands {

  private final CommentService commentService;

  @Autowired
  public CommentCommands(CommentService commentService) {
    this.commentService = commentService;
  }

  @ShellMethod(value = "insert comment command", key = {"insertComment"})
  public String insertBook(@ShellOption String text,
      @ShellOption int bookId) {
    commentService.save(new CommentDto(text, bookId));
    return "коммент сохранен!";
  }

  @ShellMethod(value = "get all comments by book id", key = {"getAllComments", "gac"})
  public String getCommentsByBookId(@ShellOption int bookId) {
    List<CommentDto> comments = commentService.getByBookId(bookId);
    return CommentConverter.commentDtosToStrings(comments);
  }

  @ShellMethod(value = "get all comments by id", key = {"getComment"})
  public String getCommentById(@ShellOption int commentId) {
    CommentDto comment = commentService.getById(commentId);
    return CommentConverter.commentDtoToString(comment);
  }

  @ShellMethod(value = "update comment", key = {"updateComment", "uc"})
  public String updateBook(@ShellOption int commentId,
      @ShellOption String text) {
    commentService.update(commentId, text);
    return "коммент изменена!";
  }

  @ShellMethod(value = "delete comment", key = {"deleteComment", "dc"})
  public String deleteBook(@ShellOption int commentId) {
    commentService.delete(commentId);
    return "книга удалена!";
  }
}
