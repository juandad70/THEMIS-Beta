package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.ApplicationLetterBusiness;
import co.sena.edu.themis.Dto.ApplicationLetterDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/application-letter")
public class ApplicationLetterController {

    @Autowired
    private ApplicationLetterBusiness applicationLetterBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllApplicationLetter() {
        try {
            List<ApplicationLetterDto> applicationLetters = applicationLetterBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = applicationLetters.stream()
                    .map(this::convertApplicationLetterDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Applications Letter retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getApplicationLetterById(@PathVariable Long id) {
        try {
            List<ApplicationLetterDto> applicationLetterDtos = applicationLetterBusiness.findById(id);
            if (applicationLetterDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpFindById("Application Letter not found", convertApplicationLetterDtoToMap(applicationLetterDtos.get(0)), HttpStatus.OK));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Application letter retrieved successfully", convertApplicationLetterDtoToMap(applicationLetterDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createApplicationLetter(@RequestBody Map<String, Object> requestBody) {
        try {
            ApplicationLetterDto applicationLetterDto = convertMapToApplicationLetterDto(requestBody);
            boolean created = applicationLetterBusiness.createApplicationLetter(applicationLetterDto);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Application Letter created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Application letter created failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateApplicationLetter(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            ApplicationLetterDto applicationLetterDto = convertMapToApplicationLetterDto(requestBody);
            applicationLetterDto.setId(id);
            boolean appliLetterUpdated = applicationLetterBusiness.updateApplicationLetter(applicationLetterDto);
            if (appliLetterUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Application letter updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Application Letter updated failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdatedError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteApplicationLetter(@PathVariable Long id){
        try {
            boolean applicationLetterDeleted = applicationLetterBusiness.deleteApplicationLetterById(id);
            if (applicationLetterDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Application letter deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Application Letter deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertApplicationLetterDtoToMap(ApplicationLetterDto applicationLetterDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", applicationLetterDto.getId());
        map.put("applica_date", applicationLetterDto.getApplica_date());
        map.put("fundament", applicationLetterDto.getFundament());
        map.put("signature", applicationLetterDto.getSignature());

        //Mapear la llave foranea de fk_id_person
        if (applicationLetterDto.getFk_id_person() != null) {
            map.put("fk_id_person", applicationLetterDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        //Mapear la llave foranea de fk_id_study_sheet
        if (applicationLetterDto.getFk_id_study_sheet() != null) {
            map.put("fk_id_study_sheet", applicationLetterDto.getFk_id_study_sheet());
        } else {
            map.put("fk_id_study_sheet", null);
        }

        //Mapear la llave foranea de fk_id_program
        if (applicationLetterDto.getFk_id_program() != null){
            map.put("fk_id_program", applicationLetterDto.getFk_id_program());
        } else {
            map.put("fk_id_program", null);
        }

        //Mapear la llave foranea de fk_id_nov_type
        if (applicationLetterDto.getFk_id_nov_type() != null) {
            map.put("fk_id_nov_type", applicationLetterDto.getFk_id_nov_type());
        } else {
            map.put("fk_id_nov_type", null);
        }

        return map;
    }

    private ApplicationLetterDto convertMapToApplicationLetterDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataobj = jsonObject.getJSONObject("data");
        ApplicationLetterDto applicationLetterDto = new ApplicationLetterDto();

        try {
            //Primero se obtiene la fecha como un String
            String applicaDateStr = dataobj.getString("applica_date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //Se cambia el String por Date
            Date applicaDate = dateFormat.parse(applicaDateStr);
            applicationLetterDto.setApplica_date(applicaDate); //Se almacena en el DTO
        } catch (ParseException e) {
            e.printStackTrace();
        }

        applicationLetterDto.setFundament(dataobj.getString("fundament"));
        applicationLetterDto.setSignature(dataobj.getString("signature"));

        return applicationLetterDto;
    }


    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}