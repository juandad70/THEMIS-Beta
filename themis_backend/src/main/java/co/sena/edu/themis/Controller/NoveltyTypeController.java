package co.sena.edu.themis.Controller;



import co.sena.edu.themis.Business.NoveltyTypeBusiness;

import co.sena.edu.themis.Dto.NoveltyTypeDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novelty-types")
public class NoveltyTypeController {

    @Autowired
    private NoveltyTypeBusiness noveltyTypeBusiness;


}