package com.study.board.file;

import com.study.board.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        List<UploadFile> sotreFileList = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                sotreFileList.add(storeFile(multipartFile));
            }
        }
        return sotreFileList;
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
