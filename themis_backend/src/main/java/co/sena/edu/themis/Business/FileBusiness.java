package co.sena.edu.themis.Business;

import co.sena.edu.themis.Entity.FileEntity;
import co.sena.edu.themis.Response.ResponseFile;
import co.sena.edu.themis.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FileBusiness {

    @Autowired
    private FileService fileService;

    // Método para almacenar un archivo y realizar alguna lógica de negocio adicional
    public FileEntity saveFile(MultipartFile file) throws IOException {
        // Aquí podrías agregar lógica adicional antes de guardar el archivo
        return fileService.store(file);
    }

    // Método para obtener un archivo por su ID, con lógica de negocio adicional
    public Optional<FileEntity> retrieveFile(UUID id) throws FileNotFoundException {
        // Podrías agregar lógica adicional, como validaciones o auditorías
        return fileService.getFile(id);
    }

    // Método para obtener todos los archivos
    public List<ResponseFile> listAllFiles() {
        // Podrías agregar lógica adicional antes de retornar la lista
        return fileService.getAllFiles();
    }

    // Puedes agregar más métodos de negocio según sea necesario
}
