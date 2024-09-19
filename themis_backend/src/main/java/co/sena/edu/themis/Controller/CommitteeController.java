package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.CommitteeBusiness;
import co.sena.edu.themis.Dto.CommitteeDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/committee")
public class CommitteeController {

    @Autowired
    private CommitteeBusiness committeeBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCommittees() {
        try {
            List<CommitteeDto> committees = committeeBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = committees.stream()
                    .map(this::convertCommitteeDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Committees retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getCommitteeById(@PathVariable Long id) {
        try {
            CommitteeDto committeeDtos = committeeBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Committee retrived successfully", convertCommitteeDtoToMap(committeeDtos), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCommittee(@RequestBody Map<String, Object> requestBody) {
        try {
            CommitteeDto committeeDto = convertMapCommitteeDto(requestBody);
            boolean committeeCreated = committeeBusiness.createCommittee(committeeDto);
            if (committeeCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Committee created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Committee creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateCommittee(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            CommitteeDto committeeDto = convertMapCommitteeDto(requestBody);
            committeeDto.setId(id);
            boolean committeeUpdated = committeeBusiness.updateCommittee(committeeDto);
            if (committeeUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Committee updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Committee update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCommittee(@PathVariable Long id) {
        try {
            boolean committeeDeleted = committeeBusiness.deleteCommitteeById(id);
            if (committeeDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Committee deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Committee deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeleteError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    private Map<String, Object> convertCommitteeDtoToMap(CommitteeDto committeeDto){
        Map<String, Object> map = new HashMap<>();
        map.put("id", committeeDto.getId());
        map.put("committeeDate", committeeDto.getCommitteeDate());

        //Mapear la llave foranea de fk_id_proceeding
        if (committeeDto.getFk_id_proceeding() != null) {
            map.put("fk_id_proceeding", committeeDto.getFk_id_proceeding());
        } else {
            map.put("fk_id_proceeding", null);
        }

        //Mapear la llave foranea de fk_id_person
        if (committeeDto.getFk_id_person() != null) {
            map.put("fk_id_person", committeeDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        return map;
    }

    private CommitteeDto convertMapCommitteeDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        CommitteeDto committeeDto = new CommitteeDto();

        String committeeDateStr = dataObject.getString("committeeDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date committeeDate = null;
        try {
            committeeDate = sdf.parse(committeeDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        committeeDto.setCommitteeDate(committeeDate);

        return committeeDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
