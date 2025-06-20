package com.pre.community.board.service;

import com.pre.community.board.domain.Board;
import com.pre.community.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 게시글_저장() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.save(any(Board.class))).willReturn(board);
        Board saved = boardService.createBoard(board);
        assertThat(saved.getTitle()).isEqualTo("제목");
        assertThat(saved.getAuthor()).isEqualTo("작성자");
    }

    @Test
    void 게시글_전체_조회() {
        Board b1 = new Board("제목1", "작성자1", "pw1", "내용1");
        Board b2 = new Board("제목2", "작성자2", "pw2", "내용2");
        given(boardRepository.findAllByOrderByCreatedAtDesc()).willReturn(Arrays.asList(b2, b1));
        List<Board> list = boardService.getAllBoards();
        assertThat(list).hasSize(2);
        assertThat(list.get(0).getTitle()).isEqualTo("제목2");
    }

    @Test
    void 게시글_단일_조회() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));
        Optional<Board> found = boardService.getBoard(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("제목");
    }

    @Test
    void 게시글_수정_성공() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));
        Optional<Board> updated = boardService.updateBoard(1L, "수정제목", "수정자", "수정내용", "pw");
        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("수정제목");
    }

    @Test
    void 게시글_수정_실패_비밀번호_불일치() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));
        Optional<Board> updated = boardService.updateBoard(1L, "수정제목", "수정자", "수정내용", "wrong");
        assertThat(updated).isNotPresent();
    }

    @Test
    void 게시글_삭제_성공() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));
        boolean deleted = boardService.deleteBoard(1L, "pw");
        assertThat(deleted).isTrue();
        verify(boardRepository, times(1)).deleteById(1L);
    }

    @Test
    void 게시글_삭제_실패_비밀번호_불일치() {
        Board board = new Board("제목", "작성자", "pw", "내용");
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));
        boolean deleted = boardService.deleteBoard(1L, "wrong");
        assertThat(deleted).isFalse();
        verify(boardRepository, never()).deleteById(any());
    }
} 