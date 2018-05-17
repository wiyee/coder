package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wiyee on 2018/5/15.
 * Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.
 * <p>
 * The Sudoku board could be partially filled, where empty cells are filled with the character'.'.
 */
public class IsValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        if (board.length < 1)
            return false;
        boolean[] visited = new boolean[board.length];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(visited, false);
            for (int j = 0; j < board.length; j++) {
                if (!process(visited, board[i][j]))
                    return false;
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            Arrays.fill(visited, false);
            for (int j = 0; j < board.length; j++) {
                if (!process(visited, board[j][i]))
                    return false;
            }
        }
        for (int i = 0; i < board.length; i += 3) {
            for (int j = 0; j < board[0].length; j += 3) {
                Arrays.fill(visited, false);
                for (int z = 0; z < board.length; z++) {
                    if (!process(visited, board[i + z / 3][j + z % 3]))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean process(boolean[] visited, char digit) {
        if (digit == '.')
            return true;
        int num = digit - '0';
        if (num < 1 || num > 9 || visited[num - 1])
            return false;
        visited[num - 1] = true;
        return true;
    }
}
