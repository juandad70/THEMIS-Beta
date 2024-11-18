package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.EventDto;
import co.sena.edu.themis.Entity.Event;
import co.sena.edu.themis.Service.EventService;
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
public class EventBusiness {

    @Autowired
    private EventService eventService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(EventBusiness.class);

    public Page<EventDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Event> events = eventService.findAll(pageRequest);

            if (events.isEmpty()) {
                logger.info("Events not found!");
            }
            Page<EventDto> eventDtoPage = events.map(Event -> modelMapper.map(Event, EventDto.class));
            return eventDtoPage;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new CustomException("Error", "Error getting events", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public EventDto findById(Long id) {
        try {
            Event event = eventService.getById(id);
            logger.info("Event: {}" + event);
            if (event != null) {
                return modelMapper.map(event, EventDto.class);
            } else {
                throw new CustomException("Not Found", "Not found event with that id", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not Found", "Not found event with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public boolean createEvent(EventDto eventDto) {
        try {
            Event event = modelMapper.map(eventDto, Event.class);
            eventService.save(event);
            logger.info("Event created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateEvent(EventDto eventDto) {
        try {
            if (eventDto.getId() == null) {
                logger.info("Can't update event because the id is null!");
            }

            Event existingEvent = eventService.getById(eventDto.getId());
            logger.info("Event: {}" + existingEvent);

            Event updatedEvent = modelMapper.map(eventDto, Event.class);
            eventService.save(updatedEvent);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The event you are trying update is not registered");
            throw new CustomException("Not Found", "Can't update the event because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteEventById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete event because the id is null");
            }

            Event deletingEvent = eventService.getById(id);
            logger.info("Event: {}" + deletingEvent);

            eventService.deleteById(id);
            logger.info("Event deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The event you are trying delete is not registered");
            throw new CustomException("Not Found", "Can't delete the event because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
