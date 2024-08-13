package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.People;
import co.sena.edu.themis.Repository.PeopleRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService implements Idao<People, Long>{

    @Autowired
    private PeopleRepository peopleRepository;


    @Override
    public List<People> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People getById(Long id) {
        return peopleRepository.getById(id);
    }

    @Override
    public void save(People people) {
        peopleRepository.save(people);
    }

    @Override
    public void delete(Long id) {
        peopleRepository.deleteById(id);

    }

    @Override
    public Page<People> findAllPage(PageRequest pageable) {
        return this.peopleRepository.findAll(pageable);
    }
}