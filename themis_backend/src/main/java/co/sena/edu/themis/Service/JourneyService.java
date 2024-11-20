package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Journey;
import co.sena.edu.themis.Repository.JourneyRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JourneyService implements Idao<Journey, Long> {

    @Autowired
    private JourneyRepository journeyRepository;

    @Override
    public List<Journey> findAll() {
        return journeyRepository.findAll();
    }

    @Override
    public Journey getById(Long id) {
        return journeyRepository.getById(id);
    }

    @Override
    public void save(Journey journey) {
        journeyRepository.save(journey);
    }

    @Override
    public void deleteById(Long id) {
        journeyRepository.deleteById(id);
    }

    @Override
    public Page<Journey> findAll(Pageable pageable) {
        return this.journeyRepository.findAll(pageable);
    }
}
