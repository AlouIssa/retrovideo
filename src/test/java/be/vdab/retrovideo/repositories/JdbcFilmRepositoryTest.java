package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(JdbcFilmRepository.class)
@Sql("/insertFilms.sql")
class JdbcFilmRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String FILMS = "films";
    private final JdbcFilmRepository repository;
    JdbcFilmRepositoryTest(JdbcFilmRepository repository) {
        this.repository = repository;
    }

    private long genreidVanFilm1(){
        return super.jdbcTemplate.queryForObject("select genreid from films where titel='Film1'", Long.class);
    }
    private long genreidVanFilm2(){
        return super.jdbcTemplate.queryForObject("select genreid from films where titel='Film2'", Long.class);
    }
    private long filmidVanFilm1(){
        return super.jdbcTemplate.queryForObject("select id from films where titel='Film1'", Long.class);
    }
    private long filmidVanFilm2(){
        return super.jdbcTemplate.queryForObject("select id from films where titel='Film2'", Long.class);
    }
    @Test
    void findAllFilmsGesorteerdOpTitel(){
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(FILMS)).extracting(film -> film.getTitel()).isSorted();
    }
    @Test   // Met meerdere elementen
    void findByGenreIdGesrtoeerdOpTitel(){
        assertThat(repository.findByGenreId(genreidVanFilm1()))
                .hasSize(super.countRowsInTableWhere(FILMS,"genreid=1"))
                .extracting(film -> film.getTitel()).isSorted();
    }
    @Test   // met 1 element
    void findByGenreId2(){
        assertThat(repository.findByGenreId(genreidVanFilm2()))
                .hasSize(super.countRowsInTableWhere(FILMS,"genreid=2"))
                .extracting(film -> film.getTitel()).isSorted();
    }
    @Test
    void findByOnbestaandeGenreIdGeeftLeeg(){
        assertThat(repository.findByGenreId(-6)).isEmpty();
    }
    @Test
    void findByFilmId(){
        assertThat(repository.findByFilmId(filmidVanFilm1()).getTitel()).isEqualTo("Film1");
    }

    @Test
    void findFilmsByIds(){
        long id1 = filmidVanFilm1();
        long id2 = filmidVanFilm2();
        assertThat(repository.findFilmsByIds(Set.of(id1,id2))).extracting(film -> film.getId()).contains(id1,id2);
    }
    @Test
    void findByIdsVanEenLegeVerzamelingGeeftLeeg(){
        assertThat(repository.findFilmsByIds(Set.of())).isEmpty();
    }
    @Test
    void findByIdsVanOnbestaandeIdsGeeftLeeg(){
        assertThat(repository.findFilmsByIds(Set.of(-1L,-2L,-3L))).isEmpty();
    }
    @Test
    void voorraadAanpassing(){
        repository.voorraadAanpassing(filmidVanFilm1());
        assertThat(super.jdbcTemplate.queryForObject("select voorraad from films where titel='Film1'",Long.class)).isEqualByComparingTo(9L);
        assertThat(super.jdbcTemplate.queryForObject("select gereserveerd from films where titel='Film1'",Long.class)).isEqualByComparingTo(1L);
    }
    @Test
    void voorraadAanpassingVanOnbestaandeFilmId(){
        assertThatExceptionOfType(FilmNietGevondenException.class).isThrownBy(()->repository.voorraadAanpassing(-6));
    }


}