package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.JourneyBusiness;
import co.sena.edu.themis.Dto.JourneyDto;
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
@RequestMapping("/api/journey")
public class JourneyController {
    @Autowired
    private JourneyBusiness journeyBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllJourney() {
        try {
            List<JourneyDto> journeys = journeyBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = journeys.stream()
                    .map(this::convertJourneyDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Journeys retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getJourneyById(@PathVariable Long id) {
        try {
            JourneyDto journeyDtos = journeyBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Journey retrieved successfully", convertJourneyDtoToMap(journeyDtos), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createJourney(@RequestBody Map<String, Object> json) {
        try {
            JourneyDto journeyDto = convertMapToJourneyDto(json);
            boolean journeyCreated = journeyBusiness.createJourney(journeyDto);
            if (journeyCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Journey created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Journey creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateJourney(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            JourneyDto journeyDto = convertMapToJourneyDto(json);
            journeyDto.setId(id);
            boolean journeyUpdated = journeyBusiness.updateJourney(journeyDto);
            if (journeyUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Journey update successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Journey update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdatedError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteJourney(@PathVariable Long id) {
        try {
            boolean journeyDeleted = journeyBusiness.deleteJourney(id);
            if (journeyDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Journey deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Journey deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertJourneyDtoToMap(JourneyDto journeyDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", journeyDto.getId());
        map.put("name", journeyDto.getName());
        return map;
    }

    private JourneyDto convertMapToJourneyDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setName(dataObj.getString("name"));
        return journeyDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException ce) {
        return ResponseEntity.status(ce.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(ce.getMessage(), ce.getHttpStatus(), ce.getTitle()));
    }
}
