package co.sena.edu.themis.Util.Http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHttpApi {

    private Object data;

    public static Map<String, Object> responseHttpFind(String result, Object data, HttpStatus codeMessage, int page, Long size){
        Map<String,Object> response = new HashMap<>();
        response.put("date",new Date());
        response.put("code",codeMessage.value());
        response.put("message",result);
        response.put("data",data);
        response.put("page",page);
        response.put("size",size);
        return response;
    }

    public static Map<String, Object> responseHttpFindById(String result, Object data, HttpStatus codeMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", codeMessage.value());
        response.put("message", result);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> responseHttpPost(String result, HttpStatus codeMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", codeMessage.value());
        response.put("message", result);
        return response;
    }

    public static Map<String, Object> responseHttpPut(String result, HttpStatus codeMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", codeMessage.value());
        response.put("message", result);
        return response;
    }

    public static Map<String, Object> responseHttpDelete(String result, HttpStatus codeMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", codeMessage.value());
        response.put("message", result);
        return response;
    }

    public static Map<String,Object> responseHttpError(String result,HttpStatus codeMessage,String data){
        Map<String,Object> response = new HashMap<>();
        response.put("date",new Date());
        response.put("code",codeMessage.value());
        response.put("message",result);
        response.put("title",data);
        return response;

    }
}