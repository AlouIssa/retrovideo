package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmRepository {
    List<Film> findAll();
    List<Film> findByGenreId(long id);
    Film findByFilmId(long id);
    List<Film> findFilmsByIds(Set<Long> ids);
    void voorraadAanpassing(long id);
}
