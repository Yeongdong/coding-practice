package com.study.board.service;

import com.study.board.entity.Article;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 글 작성
    public void write(Article article, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, filename);
        file.transferTo(saveFile);
        article.setFilename(filename);
        article.setFilepath("/files/" + filename);
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

    // 특정 게시글 삭제
    public void deleteArticle(Integer id) {
        boardRepository.deleteById(id);
    }
}
