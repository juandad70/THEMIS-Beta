package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.NoveltyBusiness;

import co.sena.edu.themis.Dto.NoveltyDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novelties")
public class NoveltyController {

    @Autowired
    private NoveltyBusiness noveltyBusiness;


}