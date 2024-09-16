package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.PersonBusiness;
import co.sena.edu.themis.Dto.PersonDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonBusiness personBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPerson() {
        try {
            List<PersonDto> people = personBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = people.stream()
                    .map(this::convertPersonDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("People retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getPersonById(@PathVariable Long id) {
        try {
            PersonDto personDto = personBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Person retrieved successfully", convertPersonDtoToMap(personDto), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPerson(@RequestBody Map<String, Object> map) {
        try {
            PersonDto personDto = convertMapToPersonDto(map);
            boolean personCreated = personBusiness.createPerson(personDto);
            if (personCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Person controller created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Person creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updatePerson(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            PersonDto personDto = convertMapToPersonDto(json);
            personDto.setId(id);
            boolean personUpdated = personBusiness.updatePerson(personDto);
            if (personUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Person updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Person update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    @DeleteMapping("/delete/{}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable Long id) {
        try {
            boolean personDeleted = personBusiness.deletePersonById(id);
            if (personDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Person deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Person deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertPersonDtoToMap(PersonDto personDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", personDto.getId());
        map.put("name", personDto.getName());
        map.put("lastname", personDto.getLastname());
        map.put("email", personDto.getEmail());
        map.put("phone", personDto.getPhone());
        map.put("status", personDto.getStatus());
        return map;
    }

    private PersonDto convertMapToPersonDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        PersonDto personDto = new PersonDto();
        personDto.setId(dataObj.getLong("id"));
        personDto.setName(dataObj.getString("name"));
        personDto.setLastname(dataObj.getString("lastname"));
        personDto.setEmail(dataObj.getString("email"));
        personDto.setPhone(dataObj.getString("phone"));
        personDto.setStatus(dataObj.getString("status"));

        return personDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }

}
