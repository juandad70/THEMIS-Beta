package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.StudySheet;
import co.sena.edu.themis.Repository.StudySheetRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudySheetService implements Idao<StudySheet, Long> {

    @Autowired
    private StudySheetRepository studySheetRepository;

    @Override
    public List<StudySheet> findAll() {
        return studySheetRepository.findAll();
    }

    @Override
    public StudySheet getById(Long id) {
        return studySheetRepository.getById(id);
    }

    @Override
    public void save(StudySheet studySheet) {
        studySheetRepository.save(studySheet);
    }

    @Override
    public void deleteById(Long id) {
        studySheetRepository.deleteById(id);
    }

    @Override
    public Page<StudySheet> findAll(Pageable pageable) {
        return this.studySheetRepository.findAll(pageable);
    }
}
