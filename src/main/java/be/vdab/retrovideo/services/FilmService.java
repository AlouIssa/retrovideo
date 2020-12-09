package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmService {
    List<Film> findAll();
    List<Film> findByGenreId(long id);
    Film findByFilmId(long id);
    List<Film> findFilmsByIds(Set<Long> ids);
    void voorraadAanpassing(long id);
}
