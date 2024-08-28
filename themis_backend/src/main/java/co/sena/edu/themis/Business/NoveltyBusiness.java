package co.sena.edu.themis.Business;


import co.sena.edu.themis.Dto.NoveltyDto;
import co.sena.edu.themis.Entity.Novelty;
import co.sena.edu.themis.Service.NoveltyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoveltyBusiness {

    @Autowired
    private NoveltyService noveltyService;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<NoveltyDto> findAll() {
        List<Novelty> novelties = noveltyService.findAll();
        return novelties.stream()
                .map(novelty -> modelMapper.map(novelty, NoveltyDto.class))
                .collect(Collectors.toList());
    }

    public NoveltyDto findById(Long id) {
        return noveltyService.findById(id)
                .map(novelty -> modelMapper.map(novelty, NoveltyDto.class))
                .orElse(null);
    }

    public NoveltyDto save(NoveltyDto noveltyDTO) {
        Novelty novelty = modelMapper.map(noveltyDTO, Novelty.class);
        novelty = noveltyService.save(novelty);
        return modelMapper.map(novelty, NoveltyDto.class);
    }

    public void deleteById(Long id) {
        noveltyService.deleteById(id);
    }
}