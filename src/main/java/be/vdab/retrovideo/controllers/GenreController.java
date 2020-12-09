package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmService;
import be.vdab.retrovideo.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GenreController {
    private final GenreService genreService;
    private final FilmService filmService;

    public GenreController(GenreService genreService, FilmService filmService) {
        this.genreService = genreService;
        this.filmService = filmService;
    }

    @GetMapping
    public ModelAndView welkom(){
        var model = new ModelAndView("genres","genres",genreService.findAll());
        return model;
    }


    @GetMapping("/{id}")
    public ModelAndView genre(@PathVariable long id){
        var modelAndView = new ModelAndView("genres","genres",genreService.findAll());
        modelAndView.addObject(filmService.findByGenreId(id)).addObject("genreNaam",genreService.findById(id).get().getNaam());
        return modelAndView;
    }

    @PostMapping("/film")
    public ModelAndView test(long id){
        return new ModelAndView("film","film", filmService.findByFilmId(id));
    }

}
