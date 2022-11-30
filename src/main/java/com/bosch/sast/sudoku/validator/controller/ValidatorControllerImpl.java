package com.bosch.sast.sudoku.validator.controller;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.dto.NewBoardDTO;
import com.bosch.sast.sudoku.validator.service.ValidatorServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ValidatorControllerImpl implements ValidatorController {

  private final ValidatorServiceImpl validatorService;

  public ValidatorControllerImpl(ValidatorServiceImpl validatorService) {
    this.validatorService = validatorService;
  }

  @Override
  public BoardDTO getBoard(long id) {
    return validatorService.getBoard(id);
  }

  @Override
  public boolean validateBoard(long id) {
    return validatorService.isValidSudoku(id);
  }

  @Override
  public BoardDTO addBoard(NewBoardDTO newBoardDTO) {
    return validatorService.saveBoard(newBoardDTO);
  }


  @Override
  public boolean deleteBoard(long boardId) {
     return validatorService.deleteBoard(boardId);
  }

  @Override
  public List<BoardDTO> getAllBoards() {
    return validatorService.getAllBoards();
  }
}
