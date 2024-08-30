package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.PersonDto;
import co.sena.edu.themis.Entity.Person;
import co.sena.edu.themis.Service.PersonService;
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
public class PersonBusiness {

    @Autowired
    private PersonService personService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(PersonBusiness.class);

    public Page<PersonDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Person> persons = personService.findAll(pageRequest);

            if (persons.isEmpty()) {
                logger.info("Persons not found!");
            }
            Page<PersonDto> personDtoPage = persons.map(Person -> modelMapper.map(Person, PersonDto.class));
            return personDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting persons", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<PersonDto> findById(Long id) {
        List<PersonDto> personDtoList = new ArrayList<>();
        try {
            Person person = personService.getById(id);
            logger.info("Person: {}" + person);
            if (person != null) {
                personDtoList.add(modelMapper.map(person, PersonDto.class));
                return personDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found person with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting person by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createPerson(PersonDto personDto) {
        try {
            Person person = modelMapper.map(personDto, Person.class);
            personService.save(person);
            logger.info("Person created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating person", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updatePerson(PersonDto personDto) {
        try {
            if (personDto.getId() == null) {
                logger.info("Can't update person because the id is null!");
            }

            Person existingPerson = personService.getById(personDto.getId());
            logger.info("Person: {}" + existingPerson);

            Person updatedPerson = modelMapper.map(personDto, Person.class);
            personService.save(updatedPerson);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The person you are trying to update is not registered");
            throw new CustomException("Not Found", "Can't update the person because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update person", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deletePersonById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete person because the id is null");
            }

            Person deletingPerson = personService.getById(id);
            logger.info("Person: {}" + deletingPerson);

            personService.deleteById(id);
            logger.info("Person deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The person you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the person because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete person", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}