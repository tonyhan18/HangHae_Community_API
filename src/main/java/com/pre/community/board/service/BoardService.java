package com.pre.community.board.service;

import com.pre.community.board.domain.Board;
import com.pre.community.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> getBoard(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public Optional<Board> updateBoard(Long id, String title, String author, String content, String password) {
        Optional<Board> boardOpt = boardRepository.findById(id);
        if (boardOpt.isPresent()) {
            Board board = boardOpt.get();
            if (board.getPassword().equals(password)) {
                board.setTitle(title);
                board.setAuthor(author);
                board.setContent(content);
                return Optional.of(board);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBoard(Long id, String password) {
        Optional<Board> boardOpt = boardRepository.findById(id);
        if (boardOpt.isPresent() && boardOpt.get().getPassword().equals(password)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 