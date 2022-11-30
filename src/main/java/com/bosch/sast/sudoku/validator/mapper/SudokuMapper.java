package com.bosch.sast.sudoku.validator.mapper;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.model.Board;
import org.springframework.stereotype.Component;

import static com.bosch.sast.sudoku.validator.Constants.BOARD_SIZE;

/**
 * As H2 DB doesn't really allow storing the "grid" IE the 2d arrays we will
 * need some trickery to map between the DTOs and entities.
 * Any ideas how to make it smarter?
 */

@Component
public class SudokuMapper {

    public BoardDTO boardToBoardDTO(Board board){
        int[][] grid = new int[BOARD_SIZE][BOARD_SIZE];
        BoardDTO boardDTO = new BoardDTO();
        for(int row = 0; row<board.getRows().size();row++){
            var columns = board.getRows().get(row).getColumns();
            for(int column=0;column<columns.size(); column++){
                grid[row][column]= columns.get(column).getValue();
            }
        }
        boardDTO.setGrid(grid);
        boardDTO.setId(board.getId());
        return boardDTO;
    }

}

