package be.vdab.retrovideo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Reservatie {
    private final long klantId;
    private final long filmtId;
    private LocalDateTime reservatie = LocalDateTime.now();

    public Reservatie(Long klantId, long filmtId) {
    this.klantId = klantId;
    this.filmtId = filmtId;
    }


    public long getKlantId() {
        return klantId;
    }

    public long getFilmtId() {
        return filmtId;
    }

    public LocalDateTime getResDatum() {
        return reservatie;
    }
}
