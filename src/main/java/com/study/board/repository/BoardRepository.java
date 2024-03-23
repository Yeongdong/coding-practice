package com.study.board.repository;

import com.study.board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Article, Integer> {
    Page<Article> findByTitleContaining(String searchKeyword, Pageable pageable);
}
