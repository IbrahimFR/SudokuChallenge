package com.bosch.sast.sudoku.validator.service;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.dto.NewBoardDTO;
import com.bosch.sast.sudoku.validator.mapper.SudokuMapper;
import com.bosch.sast.sudoku.validator.model.Board;
import com.bosch.sast.sudoku.validator.model.Column;
import com.bosch.sast.sudoku.validator.model.Row;
import com.bosch.sast.sudoku.validator.repository.ColumnRepository;
import com.bosch.sast.sudoku.validator.repository.RowRepository;
import com.bosch.sast.sudoku.validator.repository.SudokuRepository;
import com.bosch.sast.sudoku.validator.util.SudokuUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ValidatorServiceImpl implements ValidatorService {

  private final SudokuMapper sudokuMapper;
  private final SudokuRepository sudokuRepository;
  private final ColumnRepository columnRepository;
  private final RowRepository rowRepository;

  public ValidatorServiceImpl(SudokuMapper sudokuMapper, SudokuRepository sudokuRepository,
                              ColumnRepository columnRepository, RowRepository rowRepository) {
    this.sudokuMapper = sudokuMapper;
    this.sudokuRepository = sudokuRepository;
    this.columnRepository = columnRepository;
    this.rowRepository = rowRepository;
  }

  @Override
  public boolean isValidSudoku(long boardId) {
    Optional<Board> board = sudokuRepository.findById(boardId);
    if(board.isPresent()){
      BoardDTO boardDTO = sudokuMapper.boardToBoardDTO(board.get());
      return SudokuUtils.checkValidity(boardDTO.getGrid());
    }
    return false;
  }

  @Override
  public boolean isValidSudoku(int[][] grid) {
    return false;
  }

  @Override
  public BoardDTO saveBoard(NewBoardDTO newBoardDTO) {
    Board board = new Board();
    sudokuRepository.save(board);

    saveBoardCells(board,newBoardDTO);
    return sudokuMapper.boardToBoardDTO(board);
  }

  @Override
  public boolean deleteBoard(long boardId) {
    Optional<Board> board = sudokuRepository.findById(boardId);
    if(board.isPresent()){
      sudokuRepository.delete(board.get());
      return true;
    }
    return false;
  }

  @Override
  public List<BoardDTO> getAllBoards() {
    List<Board> boards = (List<Board>) sudokuRepository.findAll();
    List<BoardDTO> boardDTOs = new ArrayList<>();
    for(Board board : boards){
      BoardDTO boardDTO = sudokuMapper.boardToBoardDTO(board);
      boardDTOs.add(boardDTO);
    }
    return boardDTOs;
  }

  @Override
  public BoardDTO getBoard(long id) {
    Optional<Board> board = sudokuRepository.findById(id);
    if(board.isPresent()){
      return sudokuMapper.boardToBoardDTO(board.get());
    }
    return null;
  }

  private Board saveBoardCells(Board board, NewBoardDTO newBoardDTO){
    List<Row> rows = new ArrayList<>();
    for(int i=0; i<newBoardDTO.getGrid().length; i++) {
      Row row = new Row();
      row.setBoard(board);
      rowRepository.save(row);
      List<Column> columns = new ArrayList<>();
      for(int j=0; j<newBoardDTO.getGrid()[i].length; j++) {
        Column column = new Column();
        column.setValue(newBoardDTO.getGrid()[i][j]);
        column.setBoardId(board.getId());
        column.setRow(row);
        columnRepository.save(column);
        columns.add(column);
      }
      row.setColumns(columns);
      row.setBoard(board);
      rowRepository.save(row);
      rows.add(row);
    }
    board.setRows(rows);
    return sudokuRepository.save(board);
  }
}
