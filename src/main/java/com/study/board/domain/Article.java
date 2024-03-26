package com.study.board.domain;

import com.study.board.controller.ArticleForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String writer;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles = new ArrayList<>();

    private Article(Integer id, String title, String content, String writer, List<UploadFile> imageFiles) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.imageFiles = imageFiles;
    }

    protected Article() {

    }

    public static Article create(ArticleForm articleForm, List<UploadFile> imageFiles) {
        return new Article(null,
                articleForm.getTitle(),
                articleForm.getContent(),
                articleForm.getWriter(),
                imageFiles);
    }
}
