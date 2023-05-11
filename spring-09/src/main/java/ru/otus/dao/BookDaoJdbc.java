package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dto.BookDto;

@Repository
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public List<BookDto> getAll() {
    return namedParameterJdbcOperations.query("select * from books", new BookRowMapper());
  }

  @Override
  public void insert(BookDto bookDto) {
    namedParameterJdbcOperations.update(
        "insert into books(id, name, id_author, id_genre) values (:id, :name, :id_author, :id_genre)",
        Map.of("id", bookDto.getId(), "name", bookDto.getName(), "id_author",
            bookDto.getId_author(), "id_genre", bookDto.getId_genre()));
  }

  @Override
  public BookDto getById(long id) {
    Map<String, Long> params = Map.of("id", id);
    return namedParameterJdbcOperations.queryForObject("select * from books where id = :id", params,
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
  public BookDto getByName(String name) {
    return namedParameterJdbcOperations.queryForObject("select * from books where name = :name",
        Map.of("name", name), new BookRowMapper());
  }

  @Override
  public void updateByName(BookDto bookDto) {
    namedParameterJdbcOperations.update("update books set id_author = :id_author, id_genre = :id_genre where name = :name",
        Map.of( "name", bookDto.getName(), "id_author",
            bookDto.getId_author(), "id_genre", bookDto.getId_genre()));
  }

  private class BookRowMapper implements RowMapper<BookDto> {

    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
      String name = rs.getString("name");
      long id = rs.getLong("id");
      long id_author = rs.getLong("id_author");
      long id_genre = rs.getLong("id_genre");
      return new BookDto(id, name, id_author, id_genre);
    }
  }
}
