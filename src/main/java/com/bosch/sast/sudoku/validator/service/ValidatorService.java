package com.bosch.sast.sudoku.validator.service;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.dto.NewBoardDTO;

import java.util.List;

public interface ValidatorService {

  boolean isValidSudoku(long boardId);

  boolean isValidSudoku(int[][] grid);

  BoardDTO saveBoard(NewBoardDTO board);

  boolean deleteBoard(long boardId);

  List<BoardDTO> getAllBoards();

  BoardDTO getBoard(long id);
}
