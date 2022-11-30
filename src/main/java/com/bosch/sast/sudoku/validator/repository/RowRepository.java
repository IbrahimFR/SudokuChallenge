package com.bosch.sast.sudoku.validator.repository;

import com.bosch.sast.sudoku.validator.model.Column;
import com.bosch.sast.sudoku.validator.model.Row;
import org.springframework.data.repository.CrudRepository;

public interface RowRepository extends CrudRepository<Row, Long> {

}
