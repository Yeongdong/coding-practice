package com.study.board.service;

import com.study.board.controller.ArticleForm;
import com.study.board.domain.Article;
import com.study.board.domain.UploadFile;
import com.study.board.file.FileStore;
import com.study.board.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    FileStore fileStore;

    @AfterEach
    public void clear() {

    }

    @Test
    void 이미지없이_게시글_작성() throws IOException {
        ArticleForm articleForm = new ArticleForm("test", "test");
        Article newArticle = Article.create(articleForm);

        Integer saveArticleId = boardService.write(newArticle);

        assertThat(newArticle).isEqualTo(boardRepository.findById(saveArticleId).orElse(null));
    }

    @Test
    void 이미지하나_게시글_작성() throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("test.png", "test.png"));
        ArticleForm articleForm = new ArticleForm("test", "test");
        Article newArticle = Article.create(articleForm, uploadFiles);

        Integer saveArticleId = boardService.write(newArticle);

        assertThat(newArticle).isEqualTo(boardRepository.findById(saveArticleId).orElse(null));
        assertThat(uploadFiles.size()).isEqualTo(1);
    }

    @Test
    void 이미지두개_게시글_작성() throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("test1.png", "test2.png"));
        uploadFiles.add(new UploadFile("test2.png", "test2.png"));
        ArticleForm articleForm = new ArticleForm("test", "test");
        Article newArticle = Article.create(articleForm, uploadFiles);

        Integer saveArticleId = boardService.write(newArticle);

        assertThat(newArticle).isEqualTo(boardRepository.findById(saveArticleId).orElse(null));
        assertThat(uploadFiles.size()).isEqualTo(2);
    }

    @Test
    void 게시글_목록() throws IOException {
        for (int i = 0; i < 5; i++) {
            ArticleForm articleForm = new ArticleForm("test" + i, "test" + i);
            Article newArticle = Article.create(articleForm);

            boardService.write(newArticle);
        }
        Page<Article> articlePage = boardService.viewList(Pageable.unpaged());

        assertThat(articlePage.getSize()).isEqualTo(5);
        assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("test0");
    }

    @Test
    void 게시글_검색() throws IOException {
        for (int i = 100; i < 120; i++) {
            ArticleForm articleForm = new ArticleForm("test" + i, "test" + i);
            Article newArticle = Article.create(articleForm);

            boardService.write(newArticle);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "title");
        Pageable pageable = PageRequest.of(0, 5, sort);

        Page<Article> resultPage = boardService.viewSearchList("10", pageable);

        assertThat(resultPage.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(resultPage.getTotalPages()).isEqualTo(3);
    }

    @Test
    void 특정_게시글_불러오기() throws IOException {
        for (int i = 0; i < 5; i++) {
            ArticleForm articleForm = new ArticleForm("test" + i, "test" + i);
            Article newArticle = Article.create(articleForm);

            boardService.write(newArticle);
        }

        String title = boardService.viewArticle(1).getTitle();

        assertThat(title).isEqualTo("test0");
    }

    @Test
    void 게시글_수정() throws IOException {
        ArticleForm articleForm = new ArticleForm("test", "test");
        Article newArticle = Article.create(articleForm);

        boardService.write(newArticle);

        ArticleForm updateArticleForm = new ArticleForm("test1", "test1");
        boardService.updateArticle(1, updateArticleForm, null);

        assertThat(newArticle.getTitle()).isEqualTo("test1");
    }

    @Test
    void 게시글_삭제() throws IOException {
        ArticleForm articleForm = new ArticleForm("test", "test");
        Article newArticle = Article.create(articleForm);
        Integer articleId = boardService.write(newArticle);

        boardService.deleteArticle(articleId);

        assertThrows(NoSuchElementException.class, () -> {
            boardService.viewArticle(articleId);
        });
    }
}