package com.study.board.controller;

import com.study.board.entity.Article;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String writeArticle() {
        return "/board/writeForm";
    }

    @PostMapping("/board/write")
    public String writeArticle(Article article, Model model, @RequestParam("file") MultipartFile file) throws IOException {
        boardService.write(article, file);
        callMessage(model, "글 작성이 완료되었습니다.");
        return "message";
    }

    @GetMapping("/board/list")
    public String articleList(Model model,
                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        Page<Article> list = null;

        if (searchKeyword == null) {
            list = boardService.viewList(pageable);
        } else {
            list = boardService.viewSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());  // 페이징 알고리즘 적용 필요
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/board/articleList";
    }

    @GetMapping("/board/view")  //localhost:8080/board/view?id=1
    public String viewArticle(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("board", boardService.viewArticle(id));
        return "/board/articleViewForm";
    }

    @GetMapping("board/delete")
    public String deleteArticle(@RequestParam("id") Integer id, Model model) {
        boardService.deleteArticle(id);
        callMessage(model, "글 삭제가 완료되었습니다.");
        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String modifyArticle(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.viewArticle(id));
        return "/board/articleModifyingForm";
    }

    @PostMapping("board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Article article, Model model, @RequestParam("file") MultipartFile file) throws IOException {
        Article articleTemp = boardService.viewArticle(id);
        articleTemp.setTitle(article.getTitle());
        articleTemp.setContent(article.getContent());
        boardService.write(articleTemp, file);

        callMessage(model, "글 수정이 완료되었습니다.");
        return "message";
    }

    private void callMessage(Model model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("searchUrl", "/board/list");
    }
}
