package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@Repository
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public List<Book> getAll() {
    return namedParameterJdbcOperations.query("select b.name as name, b.id as id, p.id as personId, p.name as personName,"
        + "g.id as genreId, g.name as genreName"
        + " from books b join persons p on b.id_author = "
        + " p.id join genres g on b.id_genre = g.id", new BookRowMapper());
  }

  @Override
  public void insert(Book book) {
    namedParameterJdbcOperations.update(
        "insert into books(name, id_author, id_genre) values (:name, :id_author, :id_genre)",
        Map.of("name", book.getName(), "id_author",
            book.getAuthor().getId(), "id_genre", book.getGenre().getId()));
  }

  @Override
  public Book getById(long id) {
    Map<String, Long> params = Map.of("id", id);
    return namedParameterJdbcOperations.queryForObject("select b.name as name, b.id as id, p.id as personId, p.name as personName,"
        + "g.id as genreId, g.name as genreName"
        + " from books b join persons p on b.id_author = "
        + " p.id join genres g on b.id_genre = g.id where b.id = :id", params,
        new BookRowMapper());
  }

  @Override
  public void deleteById(long id) {
    namedParameterJdbcOperations.update("delete from books where id = :id", Map.of("id", id));
  }

  @Override
  public int count() {
    return namedParameterJdbcOperations.getJdbcOperations()
        .queryForObject("select count(*) from books", Integer.class);
  }

  @Override
  public Book getByName(String name) {
    return namedParameterJdbcOperations.queryForObject("select b.name as name, b.id as id, p.id as personId, p.name as personName,"
        + "g.id as genreId, g.name as genreName"
        + " from books b join persons p on b.id_author = "
        + " p.id join genres g on b.id_genre = g.id where b.name = :name",
        Map.of("name", name), new BookRowMapper());
  }

  @Override
  public void updateByName(Book bookDto) {
    namedParameterJdbcOperations.update("update books set id_author = :id_author, id_genre = :id_genre where name = :name",
        Map.of( "name", bookDto.getName(), "id_author",
            bookDto.getAuthor().getId(), "id_genre", bookDto.getGenre().getId()));
  }

  private static class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
      String name = rs.getString("name");
      long id = rs.getLong("id");
      long idAuthor = rs.getLong("personId");
      long idGenre = rs.getLong("genreId");
      String authorName = rs.getString("personName");
      String genreName = rs.getString("genreName");
      return new Book(id, name, new Author(idAuthor, authorName), new Genre(idGenre, genreName));
    }
  }
}
