package co.sena.edu.themis.Business;


import co.sena.edu.themis.Dto.NoveltyDto;
import co.sena.edu.themis.Entity.Novelty;
import co.sena.edu.themis.Service.NoveltyService;
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
public class NoveltyBusiness {

    @Autowired
    private NoveltyService noveltyService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(NoveltyBusiness.class);

    public Page<NoveltyDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Novelty> novelties = noveltyService.findAll(pageRequest);

            if (novelties.isEmpty()) {
                logger.info("Novelties not found!");
                return Page.empty();
            }

            Page<NoveltyDto> noveltyDtoPage = novelties.map(Novelty -> modelMapper.map(Novelty, NoveltyDto.class));
            return noveltyDtoPage;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new CustomException("Error", "Error getting novelties", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<NoveltyDto> findById(Long id) {
        List<NoveltyDto> noveltyDtoList = new ArrayList<>();
        try {
            Novelty novelty = noveltyService.getById(id);
            logger.info("Novelty: {}" + novelty);
            if (novelty != null) {
                noveltyDtoList.add(modelMapper.map(novelty, NoveltyDto.class));
                return noveltyDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found novelty with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting novelty by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createNovelty(NoveltyDto noveltyDTO) {
        try {
            Novelty novelty = modelMapper.map(noveltyDTO, Novelty.class);
            noveltyService.save(novelty);
            logger.info("Novelty created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating novelty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateNovelty(NoveltyDto noveltyDto) {
        try {
            if (noveltyDto.getId() == null) {
                logger.info("Can't update novelty because the id is null!");
            }

            Novelty existingNovelty = noveltyService.getById(noveltyDto.getId());
            logger.info("Novelty: {}" + existingNovelty);

            Novelty updatedNovelty = modelMapper.map(noveltyDto, Novelty.class);
            noveltyService.save(updatedNovelty);
            logger.info("Novelty updated successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The novelty you are trying to update is not registered");
            throw new CustomException("Error", "Can't update the novelty because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update novelty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteNoveltyById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete novelty because the id is null!!");
            }

            Novelty deletingNovelty = noveltyService.getById(id);
            logger.info("Novelty: {}" + deletingNovelty);

            noveltyService.deleteById(id);
            logger.info("Novelty deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The novelty you are trying to delete is not registered");
            throw new CustomException("Error", "Can't dedlete the novelty because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete novelty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}