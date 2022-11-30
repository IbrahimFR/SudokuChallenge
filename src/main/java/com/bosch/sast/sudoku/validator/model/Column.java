package com.bosch.sast.sudoku.validator.model;


import javax.persistence.*;

@Entity
@Table(name = "columns")
public class Column {
    @Id
    @SequenceGenerator(name="column_id_seq", sequenceName="column_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="column_id_seq")
    @javax.persistence.Column(name = "id", updatable = false)
    private Long id;

    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "row_id", nullable = false)
    private Row row;

    private int value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
