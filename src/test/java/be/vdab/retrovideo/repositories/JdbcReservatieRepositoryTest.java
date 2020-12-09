package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(JdbcReservatieRepository.class)
@Sql("/insertReservaties.sql")
class JdbcReservatieRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String RESERVATIES = "reservaties";
    private final JdbcReservatieRepository repository;
    JdbcReservatieRepositoryTest(JdbcReservatieRepository repository) {
        this.repository = repository;
    }

    @Test
    void create(){
        repository.create(new Reservatie(8L,3L));
        assertThat(super.countRowsInTableWhere(RESERVATIES, "klantid=8")).isOne();
    }

    @Test
    void selectvanEenNietAangemaakteIsZero(){
        assertThat(super.countRowsInTableWhere(RESERVATIES, "klantid=9")).isZero();
    }

}