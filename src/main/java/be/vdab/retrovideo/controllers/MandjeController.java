package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmService;
import be.vdab.retrovideo.session.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private final Mandje mandje;
    private final FilmService filmService;

    public MandjeController(Mandje mandje, FilmService filmService) {
        this.mandje = mandje;
        this.filmService = filmService;
    }
    @GetMapping
    public ModelAndView toonMandje(){
        if (mandje.getIds().isEmpty()){
            return new ModelAndView("mandje","mandje",Set.of());
        }
        var totaal = filmService.findFilmsByIds(mandje.getIds()).stream().map(film -> film.getPrijs()).reduce((vorige, getal)->vorige.add(getal)).get();
        var modelAndView = new ModelAndView("mandje","mandje", filmService.findFilmsByIds(mandje.getIds()));
        return modelAndView.addObject("totaal",totaal);
    }
    @PostMapping("{id}")
    public String toevoegen(@PathVariable long id){
        mandje.toevoegen(id);
        return "redirect:/mandje";
    }

    @PostMapping("verwijderen")
    public String verwijderen(Long[] id){
        if (id!= null){
            mandje.verwijderen(id);
        }
        return "redirect:/mandje";
    }

}
