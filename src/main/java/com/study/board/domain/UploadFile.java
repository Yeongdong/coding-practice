package com.study.board.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "upload_file")
@Data
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uploadFileName;
    private String storeFileName;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    protected UploadFile() {

    }
}
