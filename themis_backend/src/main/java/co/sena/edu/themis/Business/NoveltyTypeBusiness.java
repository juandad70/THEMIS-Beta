package co.sena.edu.themis.Business;




import co.sena.edu.themis.Dto.NoveltyTypeDto;
import co.sena.edu.themis.Entity.NoveltyType;
import co.sena.edu.themis.Service.NoveltyTypeService;
import co.sena.edu.themis.Util.Exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoveltyTypeBusiness {

    @Autowired
    private NoveltyTypeService noveltyTypeService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(NoveltyTypeBusiness.class);

    public Page<NoveltyTypeDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<NoveltyType> noveltyTypes = noveltyTypeService.findAll(pageRequest);

            if (noveltyTypes.isEmpty()) {
                logger.info("Novelties Types not found!");
            }
            Page<NoveltyTypeDto> noveltyTypeDtoPage = noveltyTypes.map(NoveltyType -> modelMapper.map(NoveltyType, NoveltyTypeDto.class));
            return noveltyTypeDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting novelties types", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<NoveltyTypeDto> findById(Long id) {
        List<NoveltyTypeDto> noveltyTypeDtoList = new ArrayList<>();
        try {
            NoveltyType noveltyType = noveltyTypeService.getById(id);
            logger.info("Novelty type: {}" + noveltyType);
            if (noveltyType != null) {
                noveltyTypeDtoList.add(modelMapper.map(noveltyType, NoveltyTypeDto.class));
                return noveltyTypeDtoList;
            } else  {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not Found", "Not found novelty type with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating novelty type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateNoveltyType(NoveltyTypeDto noveltyTypeDto) {
        try {
            if (noveltyTypeDto.getId() == null) {
                logger.info("Can't update novelty type because the id is null!");
            }

            NoveltyType existingNoveltyType = noveltyTypeService.getById(noveltyTypeDto.getId());
            logger.info("Novelty type: {}" + existingNoveltyType);

            NoveltyType updatedNoveltyType = modelMapper.map(noveltyTypeDto, NoveltyType.class);
            noveltyTypeService.save(updatedNoveltyType);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The novelty type you are trying update is not registered");
            throw new CustomException("Not Found", "Can't update the novelty type because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update novelty type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteNoveltyTypeById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete novelty type because the id is null!");
            }

            NoveltyType deletingNoveltyType = noveltyTypeService.getById(id);
            logger.info("Novelty type: {}" + deletingNoveltyType);

            noveltyTypeService.deleteById(id);
            logger.info("Novelty type deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The novelty type you are trying delete is not registered");
            throw new CustomException("Not Found", "Can't delete the novelty type because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete novelty type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}