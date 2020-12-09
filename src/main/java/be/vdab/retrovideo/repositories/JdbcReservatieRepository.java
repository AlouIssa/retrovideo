package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class JdbcReservatieRepository implements ReservatieRepository{
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Reservatie> reservatieMapper = (result, rowNum) -> new Reservatie(result.getLong("klantId"),
            result.getLong("filmId"));


    public JdbcReservatieRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template).withTableName("reservaties");
    }

    @Override
    public void create(Reservatie reservatie) {
        var kolomwaarden = Map.of("klantid", reservatie.getKlantId(),
                "filmid", reservatie.getFilmtId(), "reservatie", LocalDateTime.now());
        insert.execute(kolomwaarden);
    }
}
