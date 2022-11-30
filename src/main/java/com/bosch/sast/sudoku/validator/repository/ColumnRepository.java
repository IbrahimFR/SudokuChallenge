package com.bosch.sast.sudoku.validator.repository;

import com.bosch.sast.sudoku.validator.model.Board;
import com.bosch.sast.sudoku.validator.model.Column;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ColumnRepository extends CrudRepository<Column, Long> {
}
