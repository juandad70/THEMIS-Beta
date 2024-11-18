package co.sena.edu.themis.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileUploadService {

    @Autowired
    private S3Client s3Client;

    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        // Aquí iría la lógica para cargar el archivo a Cloudflare R2
        try {
            // Convierte el MultipartFile a un archivo temporal
            Path tempFile = Files.createTempFile(file.getOriginalFilename(), ".tmp");
            file.transferTo(tempFile.toFile());

            // Crea una solicitud para cargar el archivo
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getOriginalFilename()) // El nombre del archivo en el bucket
                    .build();

            // Carga el archivo a Cloudflare R2
            s3Client.putObject(putObjectRequest, tempFile);

            // Elimina el archivo temporal
            Files.delete(tempFile);

            return "Archivo cargado exitosamente: " + file.getOriginalFilename();
        } catch (IOException e) {
            return "Error al cargar el archivo: " + e.getMessage();
        }
    }
}
