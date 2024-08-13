package co.sena.edu.themis.Controller;


import co.sena.edu.themis.Business.NoveltyBusiness;

import co.sena.edu.themis.Dto.NoveltyDTO;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novelties")
@CrossOrigin(origins = "*")
public class NoveltyController {

    @Autowired
    private NoveltyBusiness noveltyBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAllNovelties(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        try {
            Page<NoveltyDTO> noveltyDTOList = noveltyBusiness.findAll(page, size);
            if (!noveltyDTOList.isEmpty()) {
                return new ResponseEntity<>(ResponseHttpApi.responseHttpFind("success", noveltyDTOList.getContent(), HttpStatus.OK, noveltyDTOList.getTotalPages(), noveltyDTOList.getTotalElements()), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(ResponseHttpApi.responseHttpFind("success", noveltyDTOList.getContent(), HttpStatus.OK, noveltyDTOList.getTotalPages(), noveltyDTOList.getTotalElements()), HttpStatus.OK);
            }
        } catch (CustomException customE) {
            return new ResponseEntity<>(ResponseHttpApi.responseHttpError(customE.getTitle(), customE.getHttpStatus(), customE.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> findNoveltyById(@PathVariable Long id){
        try{
            List<NoveltyDTO> noveltyDTOList = noveltyBusiness.findById(id);
            if (!noveltyDTOList.isEmpty()){
                return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("success", noveltyDTOList, HttpStatus.OK));
            }
            return new ResponseEntity<>(ResponseHttpApi.responseHttpFind("Novelty not found", new ArrayList<>(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (CustomException customE) {
            return new ResponseEntity<>(ResponseHttpApi.responseHttpError(customE.getTitle(), customE.getHttpStatus(), customE.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }



}