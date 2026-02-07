package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.service.FileStorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile) {

        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        try{
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(),-1)
                    .contentType(multipartFile.getContentType())
                    .build());
            return fileName;
        }catch(Exception e){
            throw new RuntimeException("MinIO Upload Error" +  e.getMessage());
        }

    }
}
