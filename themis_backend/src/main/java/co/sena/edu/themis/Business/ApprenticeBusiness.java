package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.ApprenticeDto;
import co.sena.edu.themis.Entity.Apprentice;
import co.sena.edu.themis.Service.ApprenticeService;
import co.sena.edu.themis.Util.Exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApprenticeBusiness {

    @Autowired
    private ApprenticeService apprenticeService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(ApprenticeBusiness.class);

    public Page<ApprenticeDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Apprentice> apprentices = apprenticeService.findAll(pageRequest);
            if (apprentices.isEmpty()) {
                logger.info("Apprentices not found!");
            }
            Page<ApprenticeDto> apprenticeDtoPage = apprentices.map(Apprentice -> modelMapper.map(Apprentice, ApprenticeDto.class));
            return apprenticeDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting apprentices", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApprenticeDto findById(Long id){
        try {
            Apprentice apprentice = apprenticeService.getById(id);
            logger.info("Apprentice: " + apprentice);
            if (apprentice != null) {
                return modelMapper.map(apprentice, ApprenticeDto.class);
            } else {
                throw new CustomException("Not Found", "Not found apprentice with that id", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not Found", "Not found apprentice with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error finding apprentice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createApprentice(ApprenticeDto apprenticeDto) {
        try {
            Apprentice apprentice = modelMapper.map(apprenticeDto, Apprentice.class);
            apprenticeService.save(apprentice);
            logger.info("Apprentice created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating apprentice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateApprentice(ApprenticeDto apprenticeDto) {
        try {
            if (apprenticeDto.getId() == null) {
                logger.info("Can't update apprentice because the id is null!");
            }
            Apprentice existingApprentice = apprenticeService.getById(apprenticeDto.getId());
            logger.info("Apprentice existing: " + existingApprentice);
            Apprentice updatedApprentice = modelMapper.map(apprenticeDto, Apprentice.class);
            apprenticeService.save(updatedApprentice);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The apprentice you are trying update is not registered");
            throw new CustomException("Not Found", "Can't update the apprentice because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error updating apprentice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteApprenticeById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete apprentice because the id is null!");
            }
            Apprentice deletingApprentice = apprenticeService.getById(id);
            logger.info("Apprentice deleting: " + deletingApprentice);
            apprenticeService.deleteById(id);
            logger.info("Apprentice delete successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.error("The apprentice you are trying delete is not registered");
            throw new CustomException("Not Found", "Can't delete the apprentice because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete apprentice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
