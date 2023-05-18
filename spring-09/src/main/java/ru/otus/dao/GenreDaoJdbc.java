package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

@Repository
public class GenreDaoJdbc implements GenreDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public List<Genre> getAll() {
    return null;
  }

  @Override
  public void insert(Genre genre) {
    namedParameterJdbcOperations.update("insert into genres(name) values (:name)",
        Map.of("name", genre.getName()));
  }

  @Override
  public Genre getById(long id) {
    Map<String, Long> params = Map.of("id", id);
    return namedParameterJdbcOperations.queryForObject("select id, name from genres where id = :id"
        , params, new GenreRowMapper());
  }

  @Override
  public void deleteById(long id) {

  }

  @Override
  public Genre getByName(String name) {
    return namedParameterJdbcOperations.queryForObject("select id, name from genres where name = :name",
        Map.of("name", name), new GenreRowMapper());
  }

  private static class GenreRowMapper implements RowMapper<Genre> {
      @Override
      public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Genre(id, name);
      }
  }
}
