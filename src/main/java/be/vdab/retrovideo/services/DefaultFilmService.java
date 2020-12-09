package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DefaultFilmService implements FilmService {
    private final FilmRepository filmRepository;

    public DefaultFilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findByGenreId(long id) {
        return filmRepository.findByGenreId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Film findByFilmId(long id) {
        return filmRepository.findByFilmId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findFilmsByIds(Set<Long> ids) {
        return filmRepository.findFilmsByIds(ids);
    }

    @Override
    public void voorraadAanpassing(long id) {
        filmRepository.voorraadAanpassing(id);
    }
}
