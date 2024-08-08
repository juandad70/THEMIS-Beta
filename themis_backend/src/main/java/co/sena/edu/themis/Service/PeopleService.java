package co.sena.edu.themis.Service;

import co.sena.edu.themis.Dto.PeopleDTO;
import co.sena.edu.themis.Entity.People;
import co.sena.edu.themis.Repository.PeopleRepository;
import co.sena.edu.themis.excepcion.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public List<PeopleDTO> getAllPeople() {
        return peopleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PeopleDTO getPeopleById(Long id) {
        return peopleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public PeopleDTO createPeople(PeopleDTO peopleDTO) {
        People people = convertToEntity(peopleDTO);
        People savedPeople = peopleRepository.save(people);
        return convertToDTO(savedPeople);
    }

    public PeopleDTO updatePeople(Long id, PeopleDTO peopleDTO) {
        People people = peopleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        people.setName(peopleDTO.getName());
        people.setLastName(peopleDTO.getLastName());
        people.setEmail(peopleDTO.getEmail());
        people.setPhone(peopleDTO.getPhone());

        People updatedPeople = peopleRepository.save(people);
        return convertToDTO(updatedPeople);
    }

    public void deletePeople(Long id) {
        if (!peopleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found with id: " + id);
        }
        peopleRepository.deleteById(id);
    }

    private PeopleDTO convertToDTO(People people) {
        return new PeopleDTO(
                people.getId(),
                people.getName(),
                people.getLastName(),
                people.getEmail(),
                people.getPhone()
        );
    }

    private People convertToEntity(PeopleDTO peopleDTO) {
        return new People(
                peopleDTO.getId(),
                peopleDTO.getName(),
                peopleDTO.getLastName(),
                peopleDTO.getEmail(),
                peopleDTO.getPhone()
        );
    }
}