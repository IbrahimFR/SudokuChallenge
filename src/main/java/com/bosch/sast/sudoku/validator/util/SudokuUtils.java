package com.bosch.sast.sudoku.validator.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SudokuUtils {

    public static boolean checkValidity(int[][] grids) {
        // Check we have 9 rows
        if (grids.length != 9) {
            return false;
        }

        // For every row...
        for (int i = 0; i < grids.length; i++) {
            // Check we have 9 columns in row
            if (grids[i].length != 9) {
                return false;
            }

            HashSet<Integer> nums = new HashSet<>();
            // For every column in row...
            for (int j = 0; j < grids[i].length; j++) {
                int current = grids[i][j];

                // Invalid if not 1 <= X <= 9
                if (current < 1 || current > 9) {
                    return false;
                }

                // Check uniqueness
                if (nums.contains(current)) {
                    return false;
                } else {
                    nums.add(current);
                }
            }
        }

        // Repeat w/ swapped i/j for column checks
        // Since it passed the first iteration, we can
        // assume:
        // - Input is a valid 9 x 9 grid
        // - There are no numbers < 1 or > 9
        for (int i = 0; i < grids[0].length; i++) {
            HashSet<Integer> nums = new HashSet<>();
            for (int j = 0; j < grids.length; j++) {
                int current = grids[j][i];
                if (nums.contains(current)) {
                    return false;
                } else {
                    nums.add(current);
                }
            }
        }

        // Check 3x3 squares. Each square is offset
        // by 3 on row or column side.
        for (int rows = 0; rows < 3; rows++) {
            int currentBoxRow = 3 * rows;
            for (int cols = 0; cols < 3; cols++) {
                int currentBoxCol = 3 * cols;
                HashSet<Integer> nums = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int current = grids[currentBoxRow + i][currentBoxCol + j];
                        if (nums.contains(current)) {
                            return false;
                        } else {
                            nums.add(current);
                        }
                    }
                }
            }
        }

        return true;
    }
}
