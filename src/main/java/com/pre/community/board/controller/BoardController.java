package com.pre.community.board.controller;

import com.pre.community.board.domain.Board;
import com.pre.community.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 전체 게시글 목록 조회
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board saved = boardService.createBoard(board);
        return ResponseEntity.ok(saved);
    }

    // 선택한 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long id) {
        Optional<Board> board = boardService.getBoard(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequest request) {
        Optional<Board> updated = boardService.updateBoard(id, request.getTitle(), request.getAuthor(), request.getContent(), request.getPassword());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteRequest request) {
        boolean deleted = boardService.deleteBoard(id, request.getPassword());
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
    }

    // JWT 인증/인가 관련 엔드포인트(구조만, 실제 구현은 추후)
    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest request) { ... }
}

class BoardUpdateRequest {
    private String title;
    private String author;
    private String content;
    private String password;
    // getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class BoardDeleteRequest {
    private String password;
    // getters and setters
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 