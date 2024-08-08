package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.FileEntity;
import co.sena.edu.themis.Response.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {

    FileEntity store(MultipartFile file) throws IOException;

    Optional<FileEntity> getFile(UUID id) throws FileNotFoundException;

    List<ResponseFile>getAllFiles();


}
