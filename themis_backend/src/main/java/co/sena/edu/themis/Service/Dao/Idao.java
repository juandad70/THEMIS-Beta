package co.sena.edu.themis.Service.Dao;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface Idao<T, Id> {
    public List<T> findAll();
    public T getById(Id id);

    @Transactional
    public void save(T obje);

    @Transactional
    public void delete(Long id);

    public Page<T> findAllPage(PageRequest pageable);
}
