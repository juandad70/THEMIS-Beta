package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.ApplicationLetterDto;
import co.sena.edu.themis.Entity.ApplicationLetter;
import co.sena.edu.themis.Service.ApplicationLetterService;
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
public class ApplicationLetterBusiness {

    @Autowired
    private ApplicationLetterService applicationLetterService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(ApplicationLetterBusiness.class);

    public Page<ApplicationLetterDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ApplicationLetter> applicationLetters = applicationLetterService.findAll(pageRequest);

            if (applicationLetters.isEmpty()){
                logger.info("Applications letters not found!");
                return Page.empty();
            }
            Page<ApplicationLetterDto> applicationLetterDtoPage = applicationLetters.map(ApplicationLetter -> modelMapper.map(ApplicationLetter, ApplicationLetterDto.class));
            return applicationLetterDtoPage;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new CustomException("Error", "Error getting applications letters", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ApplicationLetterDto> findById(Long id) {
        List<ApplicationLetterDto> applicationLetterDtoList = new ArrayList<>();
        try{
            ApplicationLetter applicationLetter = applicationLetterService.getById(id);
            logger.info("Application Letter: {}" + applicationLetter);
            if (applicationLetter != null){
                applicationLetterDtoList.add(modelMapper.map(applicationLetter, ApplicationLetterDto.class));
                return applicationLetterDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found application letter with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting application letter by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createApplicationLetter(ApplicationLetterDto applicationLetterDto) {
        try{
            ApplicationLetter applicationLetter = modelMapper.map(applicationLetterDto, ApplicationLetter.class);
            applicationLetterService.save(applicationLetter);
            logger.info("Application letter created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating application letter", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateApplicationLetter(ApplicationLetterDto applicationLetterDto) {
        try {
            if (applicationLetterDto.getId() == null) {
                logger.info("Can't update application letter because the id is null!");
            }

            ApplicationLetter existingApplicationLetter = applicationLetterService.getById(applicationLetterDto.getId());
            logger.info("Application letter: {}" + existingApplicationLetter);

            ApplicationLetter updatedApplicationLetter = modelMapper.map(applicationLetterDto, ApplicationLetter.class);
            applicationLetterService.save(updatedApplicationLetter);
            logger.info("Application letter updated successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The application letter you are trying to update is not registered");
            throw new CustomException("Not Found", "Can't update the application letter because it isn't registered!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update application letter", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteApplicationLetterById(Long id) {
        try{
            if (id == null){
                logger.info("Can't delete application letter because the id is null!");
            }

            ApplicationLetter deletingApplicationLetter = applicationLetterService.getById(id);
            logger.info("Application letter: {}" + deletingApplicationLetter);

            applicationLetterService.deleteById(id);
            logger.info("Application letter deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The application letter you are trying to delete is not registered");
            throw new CustomException("Not Found", "Can't delete the application letter because it isn't registered!", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete application letter", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
