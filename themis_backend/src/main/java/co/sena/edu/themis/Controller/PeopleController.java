package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Dto.PeopleDTO;
import co.sena.edu.themis.Service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping
    public ResponseEntity<List<PeopleDTO>> getAllPeople() {
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeopleDTO> getPeopleById(@PathVariable Long id) {
        return ResponseEntity.ok(peopleService.getPeopleById(id));
    }

    @PostMapping
    public ResponseEntity<PeopleDTO> createPeople(@Valid @RequestBody PeopleDTO peopleDTO) {
        return new ResponseEntity<>(peopleService.createPeople(peopleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeopleDTO> updatePeople(@PathVariable Long id, @Valid @RequestBody PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleService.updatePeople(id, peopleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable Long id) {
        peopleService.deletePeople(id);
        return ResponseEntity.noContent().build();
    }
}


// http://localhost:8080/api/people

//http://localhost:8080/api/people/1  put o dile