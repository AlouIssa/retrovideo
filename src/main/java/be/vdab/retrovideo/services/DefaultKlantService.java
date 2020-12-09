package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Klant;
import be.vdab.retrovideo.repositories.KlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)     // Alle methods van deze class zijn readOnly
public class DefaultKlantService implements KlantService{
    private final KlantRepository klantRepository;
    public DefaultKlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }

    @Override
    @Transactional
    public List<Klant> findByFamilieNaam(String input) {
        return klantRepository.findByFamilieNaam(input);
    }

    @Override
    @Transactional
    public Optional<Klant> findKlantById(long id) {
        return klantRepository.findKlantById(id);
    }
}
