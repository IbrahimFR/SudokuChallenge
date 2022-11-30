package com.bosch.sast.sudoku.validator;

import com.bosch.sast.sudoku.validator.dto.BoardDTO;
import com.bosch.sast.sudoku.validator.dto.NewBoardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SudokuValidatorIntegrationTest {
    private static final int[][] THE_ONE_BOARD_GRID = new int[][] {
            {4,2,5,3,7,1,9,8,6},
            {6,9,3,4,8,2,5,7,1},
            {7,1,8,9,6,5,3,2,4},
            {3,5,2,8,4,9,1,6,7},
            {9,8,7,1,2,6,4,3,5},
            {1,6,4,7,5,3,2,9,8},
            {8,3,6,5,9,4,7,1,2},
            {2,4,1,6,3,7,8,5,9},
            {5,7,9,2,1,8,6,4,3}};

    private static final int[][] THE_BAD_BOARD_GRID = new int[][] {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {9, 8, 7, 1, 2, 3, 4, 5, 6},
            {4, 5, 6, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 0, 0, 0, 0, 0, 0, 0, 0}};

    @Autowired
    private MockMvc mockMvc;


    @Test
    void uploadingTheOneBoard() throws Exception {
        final var jsonBody = new ObjectMapper().writeValueAsString(new NewBoardDTO().setGrid(THE_ONE_BOARD_GRID));
        this.mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(isA(Integer.class)))
                .andExpect(jsonPath("$.grid").value(isJsonArray(THE_ONE_BOARD_GRID)));
    }


    @Test
    void retrievingAJustUploadedBoard() throws Exception {
        long id = upload(THE_ONE_BOARD_GRID);
        this.mockMvc.perform(get("/board/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void theValidBoardIsValid() throws Exception {
        long id = upload(THE_ONE_BOARD_GRID);
        this.mockMvc.perform(get("/board/{id}/isvalid", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void theBadBoardIsInvalid() throws Exception {
        long id = upload(THE_BAD_BOARD_GRID);
        this.mockMvc.perform(get("/board/{id}/isvalid", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testDeleteBoard() throws Exception {
        long id = upload(THE_ONE_BOARD_GRID);
        this.mockMvc.perform(delete("/board/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void retrievingAllUploadedBoards() throws Exception {
        this.mockMvc.perform(get("/board/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    private long upload(int[][] board) throws Exception {
        final var jsonBody = new ObjectMapper().writeValueAsString(new NewBoardDTO().setGrid(board));
        final var mvcResult = this.mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();
        final var returnedBody = mvcResult.getResponse().getContentAsString();
        final var resultingBoard = new ObjectMapper().readValue(returnedBody, BoardDTO.class);
        return resultingBoard.getId();
    }

    private Matcher<Iterable<? extends Iterable<? extends Integer>>> isJsonArray(int[][] grid) {
        return contains(Arrays.stream(grid)
                .map(array -> contains(Arrays.stream(array).mapToObj(Matchers::is).collect(Collectors.toList())))
                .collect(Collectors.toList()));
    }
}