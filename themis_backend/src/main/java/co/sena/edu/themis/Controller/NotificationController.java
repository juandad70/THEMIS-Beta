package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.NotificationBusiness;
import co.sena.edu.themis.Dto.NotificationDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationBusiness notificationBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllNotifications() {
        try {
            List<NotificationDto> notifications = notificationBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = notifications.stream()
                    .map(this::convertNotificationDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Nootifications retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationById(@PathVariable Long id) {
        try {
            List<NotificationDto> notificationDtos = notificationBusiness.findById(id);
            if (notificationDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Notification not found", HttpStatus.NOT_FOUND, "NotificationNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Notification retrieved successfully", convertNotificationDtoToMap(notificationDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody Map<String, Object> requestBody) {
        try {
            NotificationDto notificationDto = convertMapToNotificationDto(requestBody);
            boolean notificationCreated = notificationBusiness.createNotification(notificationDto);
            if (notificationCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Notification created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Notification creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateNotification(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            NotificationDto notificationDto = convertMapToNotificationDto(requestBody);
            notificationDto.setId(id);
            boolean notificationUpdated = notificationBusiness.updateNotification(notificationDto);
            if (notificationUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Notification updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Notification update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotificationById(@PathVariable Long id){
        try {
            boolean notificationDeleted = notificationBusiness.deleteNotificationById(id);
            if (notificationDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Notification deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Notification deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertNotificationDtoToMap(NotificationDto notificationDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", notificationDto.getId());
        map.put("notifi_message", notificationDto.getNotifi_message());
        map.put("notifi_status", notificationDto.getNotifi_status());
        map.put("date_attention", notificationDto.getDate_attention());
        map.put("registration_date", notificationDto.getRegistration_date());
        return map;
    }

    private NotificationDto convertMapToNotificationDto(Map<String, Object> map) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotifi_message((String) map.get("notifi_message"));
        notificationDto.setNotifi_status((String) map.get("notifi_status"));
        notificationDto.setDate_attention((Date) map.get("date_attention"));
        notificationDto.setRegistration_date((Date) map.get("registration_date"));
        return notificationDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }

}
