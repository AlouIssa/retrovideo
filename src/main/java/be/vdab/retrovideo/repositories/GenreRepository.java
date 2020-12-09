package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findById(long id);
}
