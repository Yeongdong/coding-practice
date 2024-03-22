package com.study.board.service;

import com.study.board.entity.Article;
import com.study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    // 글 작성
    public void write(Article article, MultipartFile file) throws IOException {
        saveFile(article, file);
        boardRepository.save(article);
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

    @Transactional
    public void updateArticle(Integer id, Article article, MultipartFile file) throws IOException {
        Article updateArticle = boardRepository.findById(id).orElse(null);

        updateArticle.setTitle(article.getTitle());
        updateArticle.setContent(article.getContent());
        if (!file.isEmpty()) {
            saveFile(updateArticle, file);
        }
    }

    // 특정 게시글 삭제
    @Transactional
    public void deleteArticle(Integer id) {
        boardRepository.deleteById(id);
    }

    private void saveFile(Article article, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, filename);
        file.transferTo(saveFile);
        article.setFilename(filename);
        article.setFilepath("/files/" + filename);
    }
}
