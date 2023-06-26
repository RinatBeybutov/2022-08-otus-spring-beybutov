package ru.otus.converter;

import java.util.List;
import java.util.stream.Collectors;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;

public class CommentConverter {

  public static String commentDtosToStrings(List<CommentDto> comments) {
    StringBuilder builder = new StringBuilder();
    comments.forEach(comment -> {
      builder.append(commentDtoToString(comment) + "\n");
    });
    return builder.toString();
  }

  public static List<CommentDto> commentsToCommentDtos(List<Comment> comments) {
    return comments.stream()
        .map(comment -> new CommentDto(comment.getText(), comment.getBook().getId()))
        .collect(Collectors.toList());
  }

  public static String commentDtoToString(CommentDto comment) {
    return String.format("%s - %s", comment.getBookId(), comment.getText());
  }

  public static CommentDto commentToCommentDto(Comment comment) {
    return new CommentDto(comment.getText(), comment.getBook().getId());
  }
}
