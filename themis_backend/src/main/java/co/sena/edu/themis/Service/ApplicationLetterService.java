package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.ApplicationLetter;
import co.sena.edu.themis.Repository.ApplicationLetterRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationLetterService implements Idao<ApplicationLetter, Long> {

    @Autowired
    private ApplicationLetterRepository applicationLetterRepository;


    @Override
    public List<ApplicationLetter> findAll() {
        return applicationLetterRepository.findAll();
    }

    @Override
    public ApplicationLetter getById(Long id) {
        return applicationLetterRepository.getById(id);
    }

    @Override
    public void save(ApplicationLetter applicationLetter) {
        applicationLetterRepository.save(applicationLetter);
    }

    @Override
    public void deleteById(Long id) {
        applicationLetterRepository.deleteById(id);
    }

    @Override
    public Page<ApplicationLetter> findAll(Pageable pageable) {
        return this.applicationLetterRepository.findAll(pageable);
    }
}
