package be.vdab.retrovideo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(JdbcGenreRepository.class)
@Sql("/insertGenres.sql")
class JdbcGenreRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String GENRES = "genres";
    private final JdbcGenreRepository repository;
    JdbcGenreRepositoryTest(JdbcGenreRepository repository) {
        this.repository = repository;
    }

    private long idGenre1(){
        return super.jdbcTemplate.queryForObject("select id from genres where naam='Genre1'", long.class);
    }
    @Test
    void findAllGenresGesorteerdOpNaam(){
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(GENRES))
                .extracting(genre -> genre.getNaam()).isSorted();
    }
    @Test
    void findById(){
        assertThat(repository.findById(idGenre1()).get().getNaam()).isEqualTo("Genre1");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-6)).isEmpty();
    }

}