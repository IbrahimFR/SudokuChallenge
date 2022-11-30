package com.bosch.sast.sudoku.validator.controller;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.dto.NewBoardDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Please think about other possible operations.
 */

public interface ValidatorController {

  @ApiOperation(value = "Get board")
  @GetMapping("/board/{id}")
  BoardDTO getBoard(@PathVariable("id") long id);

  @ApiOperation(value = "Get All boards")
  @GetMapping("/board/all")
  List<BoardDTO> getAllBoards();

  @ApiOperation(value = "Check if board is valid")
  @GetMapping("/board/{id}/isvalid")
  boolean validateBoard(@PathVariable("id") long id);

  @ApiOperation(value = "Save new board")
  @PostMapping("/board")
  BoardDTO addBoard(@RequestBody NewBoardDTO boardDTO);

  @ApiOperation(value = "Delete board")
  @DeleteMapping("/board/{id}")
  boolean deleteBoard(@PathVariable("id") long id);

}
