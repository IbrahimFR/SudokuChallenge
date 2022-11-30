package com.bosch.sast.sudoku.validator.model;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.List;

/**
 * As JPA/H2 doesn't easily let us save the 2d arrays, the sudoku
 * board will be saved as a simple list.
 */

@Entity
@Table(name = "board")
public class Board {

  @Id
  @SequenceGenerator(name="board_id_seq", sequenceName="board_id_seq", allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="board_id_seq")
  @Column(name = "id", updatable = false)
  private Long id;

  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
          cascade = CascadeType.ALL)
  private List<Row> rows;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Row> getRows() {
    return rows;
  }

  public void setRows(List<Row> rows) {
    this.rows = rows;
  }
}
