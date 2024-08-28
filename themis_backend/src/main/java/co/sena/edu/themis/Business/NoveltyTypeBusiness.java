package co.sena.edu.themis.Business;




import co.sena.edu.themis.Dto.NoveltyTypeDto;
import co.sena.edu.themis.Entity.NoveltyType;
import co.sena.edu.themis.Service.NoveltyTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoveltyTypeBusiness {

    @Autowired
    private NoveltyTypeService noveltyTypeService;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<NoveltyTypeDto> findAll() {
        List<NoveltyType> noveltyTypes = noveltyTypeService.findAll();
        return noveltyTypes.stream()
                .map(noveltyType -> modelMapper.map(noveltyType, NoveltyTypeDto.class))
                .collect(Collectors.toList());
    }

    public NoveltyTypeDto findById(Long id) {
        return noveltyTypeService.findById(id)
                .map(noveltyType -> modelMapper.map(noveltyType, NoveltyTypeDto.class))
                .orElse(null);
    }

    public NoveltyTypeDto save(NoveltyTypeDto noveltyTypeDTO) {
        NoveltyType noveltyType = modelMapper.map(noveltyTypeDTO, NoveltyType.class);
        noveltyType = noveltyTypeService.save(noveltyType);
        return modelMapper.map(noveltyType, NoveltyTypeDto.class);
    }

    public void deleteById(Long id) {
        noveltyTypeService.deleteById(id);
    }
}