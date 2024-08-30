package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.ProceedingDto;
import co.sena.edu.themis.Entity.Proceeding;
import co.sena.edu.themis.Service.ProceedingService;
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
public class ProceedingBusiness {

    @Autowired
    private ProceedingService proceedingService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(ProceedingBusiness.class);

    public Page<ProceedingDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Proceeding> proceedings = proceedingService.findAll(pageRequest);

            if (proceedings.isEmpty()) {
                logger.info("Proceedings not found!");
            }
            Page<ProceedingDto> proceedingDtoPage = proceedings.map(Proceeding -> modelMapper.map(Proceeding, ProceedingDto.class));
            return proceedingDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting proceedings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ProceedingDto> findById(Long id) {
        List<ProceedingDto> proceedingDtoList = new ArrayList<>();
        try {
            Proceeding proceeding = proceedingService.getById(id);
            logger.info("Proceeding: {}" + proceeding);
            if (proceeding != null) {
                proceedingDtoList.add(modelMapper.map(proceeding, ProceedingDto.class));
                return proceedingDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found proceeding with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting proceeding by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createProceeding(ProceedingDto proceedingDto) {
        try {
            Proceeding proceeding = modelMapper.map(proceedingDto, Proceeding.class);
            proceedingService.save(proceeding);
            logger.info("Proceeding created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating proceeding", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateProceeding(ProceedingDto proceedingDto) {
        try {
            if (proceedingDto.getId() == null) {
                logger.info("Can't update proceeding because the id is null!");
            }

            Proceeding existingProceeding = proceedingService.getById(proceedingDto.getId());
            logger.info("Proceeding: {}" + existingProceeding);

            Proceeding updatedProceeding = modelMapper.map(proceedingDto, Proceeding.class);
            proceedingService.save(updatedProceeding);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The proceeding you are trying to update is not registered");
            throw new CustomException("Not Found", "Can't update the proceeding because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update proceeding", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteProceedingById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete proceeding because the id is null");
            }

            Proceeding deletingProceeding = proceedingService.getById(id);
            logger.info("Proceeding: {}" + deletingProceeding);

            proceedingService.deleteById(id);
            logger.info("Proceeding deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The proceeding you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the proceeding because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete proceeding", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}