package com.study.board.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleForm {
    private Integer id;
    private String title;
    private String content;
    private String writer;
    private List<MultipartFile> imageFiles;

    public ArticleForm() {
        this.imageFiles = new ArrayList<>();
    }

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
        this.writer = "";
        this.imageFiles = new ArrayList<>();
    }

    public ArticleForm(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.imageFiles = new ArrayList<>();
    }

    public ArticleForm(String title, String content, String writer, List<MultipartFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.imageFiles = imageFiles;
    }
}
