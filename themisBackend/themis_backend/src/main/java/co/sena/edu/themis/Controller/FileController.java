package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Entity.FileEntity;
import co.sena.edu.themis.Response.ResponseFile;
import co.sena.edu.themis.Response.ResponseMessage;
import co.sena.edu.themis.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/fileManager")
public class FileController {

    @Autowired
    private FileService fileService;
    private Map<String,Object> response;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.store(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Archivo subido exitosamente"));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) throws FileNotFoundException {
        FileEntity fileEntity = fileService.getFile(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE,fileEntity.getType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(fileEntity.getData());
    }

    @GetMapping("/files")
    public ResponseEntity<Map<String, Object>> getListFiles() {
        this.response=new HashMap<>();
        try{
            List<ResponseFile> files = fileService.getAllFiles();
            this.response.put("status","success");
            this.response.put("code", 200);
            response.put("data",files);
            //return ResponseEntity.status(HttpStatus.OK).body(files);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            this.response.put("status","bad");
            this.response.put("code", 400);
            response.put("data",e.getMessage());
            //return ResponseEntity.status(HttpStatus.OK).body(files);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }

    }
}
