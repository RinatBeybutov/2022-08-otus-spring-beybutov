package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dto.GenreDto;

@Repository
public class GenreDaoJdbc implements GenreDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  @Override
  public List<GenreDto> getAll() {
    return null;
  }

  @Override
  public void insert(GenreDto bookDto) {
    namedParameterJdbcOperations.update("insert into genres(id, name) values (:id, :name)",
        Map.of("id", bookDto.getId(), "name", bookDto.getName()));
  }

  @Override
  public GenreDto getById(long id) {
    Map<String, Long> params = Map.of("id", id);
    return namedParameterJdbcOperations.queryForObject("select * from genres where id = :id"
        , params, new GenreRowMapper());
  }

  @Override
  public void deleteById(long id) {

  }

  @Override
  public GenreDto getByName(String name) {
    return namedParameterJdbcOperations.queryForObject("select * from genres where name = :name",
        Map.of("name", name), new GenreRowMapper());
  }

  private class GenreRowMapper implements RowMapper<GenreDto> {
      @Override
      public GenreDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new GenreDto(id, name);
      }
  }
}
