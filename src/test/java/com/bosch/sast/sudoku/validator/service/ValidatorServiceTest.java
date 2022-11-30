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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ValidatorServiceTest {

    @Mock
    private SudokuRepository sudokuRepository;

    @Mock
    private RowRepository rowRepository;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private SudokuMapper sudokuMapper;

    @InjectMocks
    private ValidatorServiceImpl validatorService;

    private Board board;

    private static final int[][] BOARD_GRID = new int[][] {
            {4,2,5,3,7,1,9,8,6},
            {6,9,3,4,8,2,5,7,1},
            {7,1,8,9,6,5,3,2,4},
            {3,5,2,8,4,9,1,6,7},
            {9,8,7,1,2,6,4,3,5},
            {1,6,4,7,5,3,2,9,8},
            {8,3,6,5,9,4,7,1,2},
            {2,4,1,6,3,7,8,5,9},
            {5,7,9,2,1,8,6,4,3}};


    @BeforeEach
    public void setup(){
        board = getBoard();
    }



    @DisplayName("JUnit test for saveBoard method")
    @Test
    public void givenBoardObject_whenSaveBoard_thenReturnBoardDtoObject(){
        when(sudokuRepository.save(any())).thenReturn(board);
        when(sudokuMapper.boardToBoardDTO(any())).thenReturn(getBoardDto());

        BoardDTO boardDTO = validatorService.saveBoard(getNewBoardDto());
        // then - verify the output
        assertThat(boardDTO).isNotNull();
        assertThat(boardDTO.getId()).isEqualTo(1l);
        assertThat(boardDTO.getGrid().length).isEqualTo(9);
    }

    @DisplayName("JUnit test for getBoard method")
    @Test
    public void givenBoardObject_whenGetBoard_thenReturnBoardDtoObject(){
        when(sudokuRepository.findById(any())).thenReturn(Optional.of(board));
        when(sudokuMapper.boardToBoardDTO(any())).thenReturn(getBoardDto());

        BoardDTO boardDTO = validatorService.getBoard(1l);
        // then - verify the output
        assertThat(boardDTO).isNotNull();
        assertThat(boardDTO.getId()).isEqualTo(1l);
        assertThat(boardDTO.getGrid().length).isEqualTo(9);
    }

    @DisplayName("JUnit test for AllBoards method")
    @Test
    public void givenBoardObject_whenAllBoards_thenReturnBoardDtoObjects(){
        when(sudokuRepository.findAll()).thenReturn(List.of(board));
        when(sudokuMapper.boardToBoardDTO(any())).thenReturn(getBoardDto());

        List<BoardDTO> boards = validatorService.getAllBoards();
        // then - verify the output
        assertThat(boards).isNotNull();
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getId()).isEqualTo(1l);
        assertThat(boards.get(0).getGrid().length).isEqualTo(9);
    }

    @DisplayName("JUnit test for deleteBoard method")
    @Test
    public void givenBoardObject_whenDeleteBoard_thenReturnBooleanValue(){
        when(sudokuRepository.findById(any())).thenReturn(Optional.of(board));

        boolean isDeleted = validatorService.deleteBoard(1l);
        // then - verify the output
        assertThat(isDeleted).isEqualTo(true);
    }

    private NewBoardDTO getNewBoardDto(){
        NewBoardDTO boardDTO = new NewBoardDTO();
        boardDTO.setGrid(BOARD_GRID);
        return boardDTO;
    }

    private BoardDTO getBoardDto(){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setGrid(BOARD_GRID);
        boardDTO.setId(1l);
        return boardDTO;
    }

    private Board getBoard(){
        Board board = new Board();
        List<Row> rows = new ArrayList<>();
        for(int i=0; i<BOARD_GRID.length; i++) {
            Row row = new Row();
            row.setBoard(board);
            List<Column> columns = new ArrayList<>();
            for(int j=0; j<BOARD_GRID[i].length; j++) {
                Column column = new Column();
                column.setValue(BOARD_GRID[i][j]);
                column.setBoardId(board.getId());
                column.setRow(row);
                column.setId(Long.valueOf(j));
                columns.add(column);
            }
            row.setColumns(columns);
            row.setBoard(board);
            row.setId(Long.valueOf(i));
            rows.add(row);
        }
        board.setRows(rows);
        board.setId(1l);
        return board;
    }
}
