package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dto.PersonDto;

@Repository
public class PersonDaoJdbc implements PersonDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public PersonDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations.getJdbcOperations()
            .queryForObject("select count(*) from persons", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(PersonDto personDto) {
        namedParameterJdbcOperations.update("insert into persons (id, name) values (:id, :name)",
                Map.of("id", personDto.getId(), "name", personDto.getName()));
    }

    @Override
    public PersonDto getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from persons where id = :id", params, new PersonRowMapper()
        );
    }

    @Override
    public List<PersonDto> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select id, name from persons", new PersonRowMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from persons where id = :id", params
        );
    }

    @Override
    public PersonDto getByName(String author) {
        return namedParameterJdbcOperations.queryForObject("select * from persons where name = :name",
            Map.of("name", author), new PersonRowMapper());
    }

    private static class PersonRowMapper implements RowMapper<PersonDto> {

        @Override
        public PersonDto mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new PersonDto(id, name);
        }
    }
}
