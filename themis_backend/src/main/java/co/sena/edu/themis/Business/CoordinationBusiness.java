package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.CoordinationDto;
import co.sena.edu.themis.Entity.Coordination;
import co.sena.edu.themis.Service.CoordinationService;
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

@Component
public class CoordinationBusiness {

    @Autowired
    private CoordinationService coordinationService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(CoordinationBusiness.class);

    public Page<CoordinationDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Coordination> coordinations = coordinationService.findAll(pageRequest);

            if (coordinations.isEmpty()) {
                logger.info("Coordinations not found!");
            }
            Page<CoordinationDto> coordinationDtoPage = coordinations.map(Coordination -> modelMapper.map(Coordination, CoordinationDto.class));
            return coordinationDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting coordinations", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<CoordinationDto> findById(Long id) {
        List<CoordinationDto> coordinationDtoList = new ArrayList<>();
        try {
            Coordination coordination = coordinationService.getById(id);
            logger.info("Coordination: {}" + coordination);
            if (coordination != null) {
                coordinationDtoList.add(modelMapper.map(coordination, CoordinationDto.class));
                return coordinationDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found coordination with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting coordination by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createCoordination(CoordinationDto coordinationDto) {
        try {
            Coordination coordination = modelMapper.map(coordinationDto, Coordination.class);
            coordinationService.save(coordination);
            logger.info("Coordination created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating coordination", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateCoordination(CoordinationDto coordinationDto) {
        try {
            if (coordinationDto.getId() == null) {
                logger.info("Can't update coordination because the id is null!");
            }

            Coordination existingCoordination = coordinationService.getById(coordinationDto.getId());
            logger.info("Coordination: {}" + existingCoordination);

            Coordination updatedCoordination = modelMapper.map(coordinationDto, Coordination.class);
            coordinationService.save(updatedCoordination);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The coordination you are tying to update is not registered");
            throw new CustomException("Not Found", "Can't update the coordination because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update coordination", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteCoordinationById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete coordination because the id is null");
            }

            Coordination deletingCoordination = coordinationService.getById(id);
            logger.info("Coordination: {}" + deletingCoordination);

            coordinationService.deleteById(id);
            logger.info("Coordination deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The coordination you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the coordination because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete coordination", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
