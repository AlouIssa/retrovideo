package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcFilmRepository implements FilmRepository {
    private final JdbcTemplate template;
    public JdbcFilmRepository(JdbcTemplate template) {
        this.template = template;
    }
    private final RowMapper<Film> filmMapper = ((result, rowNum) -> new Film(result.getLong("id")
            ,result.getLong("genreid"), result.getString("titel")
            ,result.getLong("voorraad"), result.getLong("gereserveerd")
            ,result.getBigDecimal("prijs"))) ;

    @Override
    public List<Film> findAll() {
        var sql = "select id, genreid, titel, voorraad, gereserveerd, prijs from films order by titel";
        return template.query(sql, filmMapper);
    }

    @Override
    public List<Film> findByGenreId(long id) {
            var sql = "select id, genreid, titel, voorraad, gereserveerd, prijs from films where genreid=? order by titel";
            return template.query(sql, filmMapper, id);
    }

    @Override
    public Film findByFilmId(long id) {
        var sql = "select id, genreid, titel, voorraad, gereserveerd, prijs  from films where id=?";
        return template.queryForObject(sql, filmMapper, id);
    }

    @Override
    public List<Film> findFilmsByIds(Set<Long> ids) {
        if (ids.isEmpty()){
            return List.of();
        }
        var sql = "select id, genreid, titel, voorraad, gereserveerd, prijs from films " +
                "where id in (" + "?, ".repeat(ids.size()-1) + "?)";
        return template.query(sql, ids.toArray(), filmMapper);
    }

    @Override
    public void voorraadAanpassing(long id) {
        var sql = "update films set gereserveerd=gereserveerd+1, voorraad=voorraad-1 where id=?";
        if (template.update(sql, id)==0){
            throw new FilmNietGevondenException();
        }
    }
}
