package org.bitcamp.devlog.service.minio;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorage {

    String uploadFile(String bucketName, String directory, MultipartFile file);

    void deleteFile(String bucketName, String directory, String fileName);

}
