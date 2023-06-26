package ru.otus.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;


@DisplayName("Репозиторий Jpa для работы с комментами")
@DataJpaTest
@Import(CommentDaoJpa.class)
public class CommentDaoJpaTest {

  private static final long EXISTING_BOOK_ID = 1;
  private static final long EXISTING_ID_AUTHOR = 1;
  private static final String EXISTING_NAME_AUTHOR = "Ivan";
  private static final long EXISTING_ID_GENRE = 1;
  private static final String EXISTING_NAME_GENRE = "detective";
  private static final String EXISTING_BOOK_NAME = "War and peace";
  private static final long EXISTING_COMMENT_ID = 1;
  private static final String EXISTING_COMMENT_TEXT = "Good book";

  @Autowired
  private CommentDaoJpa commentDao;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Вставка коммента")
  void insertBookTest() {
    Comment expectedComment = new Comment();
    expectedComment.setText("New expected comment");
    expectedComment.setBook(getExistingBook());
    expectedComment.setId(2);
    commentDao.insert(expectedComment);

    Comment actualComment = commentDao.getCommentById(2);

    assertThat(actualComment.getText()).isEqualTo(expectedComment.getText());
  }

  @Test
  @DisplayName("Получение комментов у книги")
  void getCommentsByBookIdTest() {
    Comment expectedComment = getExistingComment();
    List<Comment> actualComments = commentDao.getAllByBookId(EXISTING_BOOK_ID);
    assertThat(actualComments).containsExactlyInAnyOrder(expectedComment);
  }

  @Test
  @DisplayName("Получение коммента по ID")
  void getCommentByIdTest() {
    Comment expectedComment = getExistingComment();
    Comment actualComment = commentDao.getCommentById(EXISTING_COMMENT_ID);
    assertThat(actualComment).isEqualTo(expectedComment);
  }

  @Test
  @DisplayName("Удаление комментария")
  void deleteCommentTest() {
    commentDao.deleteById(EXISTING_COMMENT_ID);
    Comment nullComment = commentDao.getCommentById(EXISTING_COMMENT_ID);
    assertThat(nullComment).isNull();
  }

  @Test
  @DisplayName("Обновление коммента")
  void updateCommentTest() {
    commentDao.updateById("edited text", EXISTING_COMMENT_ID);
    Comment actualComment = commentDao.getCommentById(EXISTING_COMMENT_ID);
    assertThat(actualComment.getText()).isEqualTo("edited text");

  }

  private Book getExistingBook() {
    Book book = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, null,
        null, null);
    List<Book> books = Arrays.asList(book);
    Author author = new Author(EXISTING_ID_AUTHOR, EXISTING_NAME_AUTHOR, books);
    Genre genre = new Genre(EXISTING_ID_GENRE, EXISTING_NAME_GENRE, books);
    book.setAuthor(author);
    book.setGenre(genre);
    return book;
  }

  private Comment getExistingComment() {
    Comment expectedComment = new Comment();
    expectedComment.setText(EXISTING_COMMENT_TEXT  );
    expectedComment.setBook(getExistingBook());
    expectedComment.setId(EXISTING_COMMENT_ID);
    return expectedComment;
  }
}
