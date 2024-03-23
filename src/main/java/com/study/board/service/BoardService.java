package com.study.board.service;

import com.study.board.controller.ArticleForm;
import com.study.board.domain.Article;
import com.study.board.domain.UploadFile;
import com.study.board.file.FileStore;
import com.study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    // 글 작성
    @Transactional
    public void write(ArticleForm articleForm, List<UploadFile> imageFiles) throws IOException {
        Article newArticle = Article.create(articleForm, imageFiles);
        for (UploadFile uploadFile : imageFiles) {
            uploadFile.setArticle(newArticle);
        }
        boardRepository.save(newArticle);
    }

    // 게시글 리스트 처리
    public Page<Article> viewList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Article> viewSearchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Article viewArticle(Integer id) {
        return boardRepository.findById(id).get();
    }

    // 게시글 수정
    @Transactional
    public void updateArticle(Integer id, ArticleForm articleForm, List<MultipartFile> imageFiles) throws IOException {
        Article updateArticle = boardRepository.findById(id).orElse(null);

        updateArticle.setTitle(articleForm.getTitle());
        updateArticle.setContent(articleForm.getContent());
        if (!imageFiles.isEmpty()) {
            updateArticle.setImageFiles(fileStore.storeFiles(imageFiles));
        }
    }

    // 특정 게시글 삭제
    @Transactional
    public void deleteArticle(Integer id) {
        boardRepository.deleteById(id);
    }
}
