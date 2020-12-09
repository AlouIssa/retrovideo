package be.vdab.retrovideo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(JdbcKlantRepository.class)
@Sql("/insertKlanten.sql")
class JdbcKlantRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String KLANTEN = "klanten";
    private final JdbcKlantRepository repository;
    JdbcKlantRepositoryTest(JdbcKlantRepository repository) {
        this.repository = repository;
    }

    private long klantIdfromKlant1(){
        return super.jdbcTemplate.queryForObject("select id from klanten where familienaam='fNaam1'", Long.class);
    }
    @Test
    void findByFamilieNaam(){
        assertThat(repository.findByFamilieNaam("aa")).hasSize(super
                .countRowsInTableWhere(KLANTEN,"familienaam like '%aa%'"))
                .extracting(klant -> klant.getFamilienaam()).isSorted();
    }
    @Test
    void findByOnbestaandeFamilieNaamGeeftLeeg(){
        assertThat(repository.findByFamilieNaam("xxx")).isEmpty();
    }

    @Test
    void findKlantById(){
        assertThat(repository.findKlantById(klantIdfromKlant1()).get().getFamilienaam()).isEqualTo("fNaam1");
    }
    @Test
    void findKlantByOnbestaandeIdGeeftLeeg(){
        assertThat(repository.findKlantById(-6)).isEmpty();
    }
}