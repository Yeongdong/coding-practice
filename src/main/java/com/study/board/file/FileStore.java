package com.study.board.file;

import com.study.board.domain.Article;
import com.study.board.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileList.add(storeFile(multipartFile));
            }
        }
        return storeFileList;
    }

    public ResponseEntity<Resource> downloadFiles(Article article, String fileName) throws MalformedURLException {

        String uploadFileName = article.getImageFiles()
                .stream()
                .filter(file -> file.getStoreFileName().equals(fileName))
                .findFirst()
                .orElse(null)
                .getUploadFileName();

        UrlResource resource = new UrlResource("file://" + getFullPath(fileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        UUID uuid = UUID.randomUUID();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int position = originalFileName.lastIndexOf(".");
        return originalFileName.substring(position + 1);
    }
}
