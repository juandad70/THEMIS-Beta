package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.JourneyDto;
import co.sena.edu.themis.Entity.Journey;
import co.sena.edu.themis.Service.JourneyService;
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
public class JourneyBusiness {

    @Autowired
    private JourneyService journeyService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(JourneyBusiness.class);

    public Page<JourneyDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Journey> journeys = journeyService.findAll(pageRequest);
            if (journeys.isEmpty()) {
                logger.info("Journeys not found!");
            }
            Page<JourneyDto> journeyDtoPage = journeys.map(Journey -> modelMapper.map(Journey, JourneyDto.class));
            return journeyDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting journeys", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public JourneyDto findById(Long id) {
        try {
            Journey journey = journeyService.getById(id);
            logger.info("Journey: " + journey);
            if (journey != null) {
                return modelMapper.map(journey, JourneyDto.class);
            } else {
                throw new CustomException("Not Found", "Not found journey with that id", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not Found", "Not found journey with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error finding journey", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createJourney(JourneyDto journeyDto) {
        try {
            Journey journey = modelMapper.map(journeyDto, Journey.class);
            journeyService.save(journey);
            logger.info("Journey created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating journey", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateJourney(JourneyDto journeyDto) {
        try {
            if (journeyDto.getId() == null) {
                logger.info("Can't update journey because the id is null!");
            }
            Journey existingJourney = journeyService.getById(journeyDto.getId());
            logger.info("Journey existing: " + existingJourney);

            Journey updatedJourney = modelMapper.map(journeyDto, Journey.class);
            journeyService.save(updatedJourney);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The journey you are trying update is not registered");
            throw new CustomException("Not Found", "Can't update the journey because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update journey", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteJourney(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete journey because the id is null!");
            }
            Journey deletingJourney = journeyService.getById(id);
            logger.info("Journey deleting: " + deletingJourney);
            journeyService.deleteById(id);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The journey you are trying delete is not registered");
            throw new CustomException("Not Found", "Can't delete the journey because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete journey", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
