package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcKlantRepository implements KlantRepository{
    private final JdbcTemplate template;
    public JdbcKlantRepository(JdbcTemplate template) {
        this.template = template;
    }
    private final RowMapper<Klant> klantRowMapper = ((result, rowNum) -> new Klant(result.getLong("id"),
            result.getString("familienaam"), result.getString("voornaam"),
            result.getString("straatNummer"), result.getString("postcode"),
            result.getString("gemeente")));

    @Override
    public List<Klant> findByFamilieNaam(String input) {
        var sql = "select id, familienaam, voornaam, straatNummer, postcode, gemeente " +
                "from klanten where familienaam like ? order by familienaam";
        return template.query(sql, klantRowMapper, '%'+ input+ '%');
    }

    @Override
    public Optional<Klant> findKlantById(long id) {
        try {
            var sql = "select id, familienaam, voornaam, straatNummer, postcode, gemeente" +
                    " from klanten where id=?";
            return Optional.of(template.queryForObject(sql, klantRowMapper, id));
        }catch (IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }
}
