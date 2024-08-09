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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/fileManager")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.store(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Archivo subido correctamente"));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) throws FileNotFoundException {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                    .body(fileEntity.getData());
        } else {
            throw new FileNotFoundException("Archivo no encontrado con ID: " + id);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<Map<String, Object>> getList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ResponseFile> files = fileService.getAllFiles();
            response.put("status", "success");
            response.put("code", 200);
            response.put("data", files);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "bad");
            response.put("code", 400);
            response.put("data", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
