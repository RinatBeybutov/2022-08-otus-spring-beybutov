package ru.otus.command;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.converter.CommentConverter;
import ru.otus.dto.CommentDto;
import ru.otus.service.CommentService;

@ShellComponent
public class CommentCommands {

  private final CommentService commentService;

  private final CommentConverter commentConverter;

  @Autowired
  public CommentCommands(CommentService commentService, CommentConverter commentConverter) {
    this.commentService = commentService;
    this.commentConverter = commentConverter;
  }

  @ShellMethod(value = "insert comment command", key = {"insertComment"})
  public String insertBook(@ShellOption String text,
      @ShellOption int commentId) {
    commentService.save(new CommentDto(text, commentId));
    return "коммент сохранен!";
  }

  @ShellMethod(value = "get all comments by book id", key = {"getAllComments", "gac"})
  public String getCommentsByBookId(@ShellOption int bookId) {
    List<CommentDto> comments = commentService.getByBookId(bookId);
    return commentConverter.commentDtosToStrings(comments);
  }

  @ShellMethod(value = "get all comments by id", key = {"getComment"})
  public String getCommentById(@ShellOption int commentId) {
    CommentDto comment = commentService.getById(commentId);
    return commentConverter.commentDtoToString(comment);
  }

  @ShellMethod(value = "update comment", key = {"updateComment", "uc"})
  public String updateComment(@ShellOption int commentId,
      @ShellOption String text) {
    commentService.update(commentId, text);
    return "коммент изменена!";
  }

  @ShellMethod(value = "delete comment", key = {"deleteComment", "dc"})
  public String deleteComment(@ShellOption int commentId) {
    commentService.delete(commentId);
    return "книга удалена!";
  }
}
