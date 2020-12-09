package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.forms.FamilieNaamForm;
import be.vdab.retrovideo.services.FilmService;
import be.vdab.retrovideo.services.KlantService;
import be.vdab.retrovideo.services.ReservatieService;
import be.vdab.retrovideo.session.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@SessionScope
@RequestMapping("klant")
public class KlantController implements Serializable {
    private static final long SerialVersionUID = 1L;
    private final KlantService klantService;
    private final FilmService filmService;
    private final ReservatieService reservatieService;
    private final Mandje mandje;

    public KlantController(KlantService klantService, FilmService filmService, ReservatieService reservatieService, Mandje mandje) {
        this.klantService = klantService;
        this.filmService = filmService;
        this.reservatieService = reservatieService;
        this.mandje = mandje;
    }

    @GetMapping
    public ModelAndView toonKlanten(@Valid FamilieNaamForm form, Errors errors) {
        var modelAndView = new ModelAndView("klant");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("klanten", klantService.findByFamilieNaam(form.getFamilieNaamInput()));
    }

    @GetMapping("form")
    public ModelAndView familieNaamForm() {
        return new ModelAndView("klant").addObject(new FamilieNaamForm(""));
    }

    @GetMapping("/{id}/bevestigen")
    public ModelAndView bevestigen(@PathVariable long id) {
        return new ModelAndView("bevestigen", "klant", klantService.findKlantById(id).get())
                .addObject("aantal", mandje.aantalInMandje());
    }

    @PostMapping("/{id}/rapport")
    public ModelAndView rapport(@PathVariable long id) {
        Boolean gereserveerd = false;
        Set<Film> ontbrekendfilms = new LinkedHashSet<Film>();
        Set<Long> ontbrekendIds = new LinkedHashSet<Long>();
        for (var een : mandje.getIds()) {
            if (filmService.findByFilmId(een).getVoorraad() > 0) {
                gereserveerd = true;
                System.out.println(een);
                reservatieService.create(new Reservatie(id, een));
                filmService.voorraadAanpassing(een);
            } else
                ontbrekendIds.add(een);
        }
        ontbrekendIds.stream().forEach(filmId-> ontbrekendfilms.add(filmService.findByFilmId(filmId)));
        mandje.reset();
        return new ModelAndView("rapport", "ontbrekend", ontbrekendfilms)
            .addObject("gereserveerd", gereserveerd);
    }
}
