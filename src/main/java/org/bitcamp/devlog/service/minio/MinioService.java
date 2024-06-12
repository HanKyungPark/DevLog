package org.bitcamp.devlog.service.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.example.blogproject.config.MinioConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioService implements ObjectStorage {

    private final MinioClient client;

    public MinioService(MinioConfig config) {
        this.client = config.initMinioClient();
    }


    @Override
    public String uploadFile(String bucketName, String directory, MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        try {
            client.putObject(PutObjectArgs.builder()
                    .object(directory+"/"+fileName)
                    .contentType(file.getContentType())
                    .bucket(bucketName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (ServerException | ErrorResponseException | InsufficientDataException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public void deleteFile(String bucketName, String directory, String fileName) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(directory+"/"+fileName).build());
        } catch (ServerException | ErrorResponseException | InsufficientDataException | IOException |
                NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                InternalException e) {
            throw new RuntimeException(e);
        }
    }
}
