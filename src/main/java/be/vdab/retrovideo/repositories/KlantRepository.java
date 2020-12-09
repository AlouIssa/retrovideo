package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Klant;

import java.util.List;
import java.util.Optional;

public interface KlantRepository {
    List<Klant> findByFamilieNaam(String input);
    Optional<Klant> findKlantById(long id);
}
