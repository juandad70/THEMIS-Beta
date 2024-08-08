package co.sena.edu.themis.Service;


import co.sena.edu.themis.Dto.NoveltyDTO;
import co.sena.edu.themis.Entity.Novelty;
import co.sena.edu.themis.Repository.NoveltyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoveltyService {

    @Autowired
    private NoveltyRepository noveltyRepository;

    public NoveltyDTO createNovelty(NoveltyDTO noveltyDTO) {
        Novelty novelty = new Novelty();
        BeanUtils.copyProperties(noveltyDTO, novelty);
        novelty = noveltyRepository.save(novelty);
        BeanUtils.copyProperties(novelty, noveltyDTO);
        return noveltyDTO;
    }

    public NoveltyDTO updateNovelty(Long id, NoveltyDTO noveltyDTO) {
        Novelty existingNovelty = noveltyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Novelty not found"));
        BeanUtils.copyProperties(noveltyDTO, existingNovelty, "id");
        existingNovelty = noveltyRepository.save(existingNovelty);
        BeanUtils.copyProperties(existingNovelty, noveltyDTO);
        return noveltyDTO;
    }

    public void deleteNovelty(Long id) {
        noveltyRepository.deleteById(id);
    }

    public NoveltyDTO getNovelty(Long id) {
        Novelty novelty = noveltyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Novelty not found"));
        NoveltyDTO noveltyDTO = new NoveltyDTO();
        BeanUtils.copyProperties(novelty, noveltyDTO);
        return noveltyDTO;
    }

    public List<NoveltyDTO> getAllNovelties() {
        List<Novelty> novelties = noveltyRepository.findAll();
        return novelties.stream()
                .map(novelty -> {
                    NoveltyDTO noveltyDTO = new NoveltyDTO();
                    BeanUtils.copyProperties(novelty, noveltyDTO);
                    return noveltyDTO;
                })
                .collect(Collectors.toList());
    }
}