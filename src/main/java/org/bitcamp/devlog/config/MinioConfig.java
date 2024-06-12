package org.bitcamp.devlog.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Data
public class MinioConfig {

    private String accessKey = System.getenv("MINIO_ACCESS_KEY");
    private String secretKey = System.getenv("MINIO_SECRET_KEY");
    private String url = System.getenv("MINIO_URL");
    private MinioClient minioClient;


    public MinioClient initMinioClient(){
        minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey,secretKey)
                .build();
        try{
            minioClient.ignoreCertCheck();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return minioClient;
    }
}
