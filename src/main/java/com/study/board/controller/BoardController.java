package com.study.board.controller;

import com.study.board.domain.Article;
import com.study.board.domain.UploadFile;
import com.study.board.file.FileStore;
import com.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileStore fileStore;

    @GetMapping("/board/write")
    public String writeArticle() {
        return "/board/writeForm";
    }

    @PostMapping("/board/write")
    public String writeArticle(ArticleForm articleForm, Model model) throws IOException {
        List<UploadFile> storeImageFiles = fileStore.storeFiles(articleForm.getImageFiles());

        boardService.write(articleForm, storeImageFiles);
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

    @GetMapping("/board/delete")
    public String deleteArticle(@RequestParam("id") Integer id, Model model) {
        boardService.deleteArticle(id);
        callMessage(model, "글 삭제가 완료되었습니다.");
        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String modifyArticle(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("article", boardService.viewArticle(id));
        return "/board/articleModifyingForm";
    }

    @PostMapping("/board/modify/{id}")
    public String modifyArticle(@PathVariable("id") Integer id, ArticleForm articleForm, Model model, @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException {
        boardService.updateArticle(id, articleForm, imageFiles);

        callMessage(model, "글 수정이 완료되었습니다.");
        return "message";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Resource resource = new ClassPathResource("static/images/" + filename);

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void callMessage(Model model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("searchUrl", "/board/list");
    }
}
