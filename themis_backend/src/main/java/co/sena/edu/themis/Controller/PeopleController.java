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

}


// http://localhost:8080/api/people

//http://localhost:8080/api/people/1  put o dile

