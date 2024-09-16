package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.EventBusiness;
import co.sena.edu.themis.Dto.EventDto;
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
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventBusiness eventBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllEvent() {
        try {
            List<EventDto> eventDtos = eventBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = eventDtos.stream()
                    .map(this::convertEventDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Events retrieved successffully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getEventById(@PathVariable Long id) {
        try {
            EventDto eventDtos = eventBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Event retrived successfully", convertEventDtoToMap(eventDtos), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createEvent(@RequestBody Map<String, Object> requestBody) {
        try {
            EventDto eventDto = convertMapToEventDto(requestBody);
            boolean eventCreated = eventBusiness.createEvent(eventDto);
            if (eventCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Event created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Event creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationFailed"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEvent(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            EventDto eventDto = convertMapToEventDto(requestBody);
            eventDto.setId(id);
            boolean eventUpdated = eventBusiness.updateEvent(eventDto);
            if (eventUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Event updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Event update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdatedFailed"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteEventById(@PathVariable Long id) {
        try {
            boolean eventDeleted = eventBusiness.deleteEventById(id);
            if (eventDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Event deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Event deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletedFailed"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    private Map<String, Object> convertEventDtoToMap(EventDto eventDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", eventDto.getId());
        map.put("eventFile", eventDto.getEventFile());

        //Mapear llave foranea de fk_id_proceeding
        if (eventDto.getFk_id_proceeding() != null) {
            map.put("fk_id_proceeding", eventDto.getFk_id_proceeding());
        } else {
            map.put("fk_id_proceeding", null);
        }

        //Mapear llave foranea de fk_id_person
        if (eventDto.getFk_id_person() != null) {
            map.put("fk_id_person", eventDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        return map;
    }

    private EventDto convertMapToEventDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        EventDto eventDto = new EventDto();
        eventDto.setId(dataObj.getLong("id"));
        eventDto.setEventFile(dataObj.getString("eventFile"));
        return eventDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
