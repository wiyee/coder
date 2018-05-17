package leetcode;

/**
 * Created by wiyee on 2018/5/3.
 * <p>
 * Given a 2D board and a word, find if the word exists in the grid.
 * <p>
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
 * <p>
 * For example,
 * Given board =
 * [
 * ["ABCE"],
 * ["SFCS"],
 * ["ADEE"]
 * ]
 * word ="ABCCED", -> returnstrue,
 * word ="SEE", -> returnstrue,
 * word ="ABCB", -> returnsfalse.
 * 回溯法
 */
public class Exist {

    public boolean exist(char[][] board, String word) {
        if (board.length < 1 || word.length() < 1)
            return false;
        boolean sign = false;
        boolean[][] visit = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0, visit))
                        return true;
                }
            }
        }
        return sign;
    }

    public static boolean dfs(char[][] board, String word, int row, int col, int index, boolean[][] visit) {
        int m = board.length;
        int n = board[0].length;
        if (index >= word.length())
            return true;
        if (row < 0 || col < 0 || row >= m || col >= n || board[row][col] != word.charAt(index))
            return false;
        if (visit[row][col])
            return false;
        visit[row][col] = true;
        boolean sign = dfs(board, word, row - 1, col, index + 1, visit) ||
                dfs(board, word, row + 1, col, index + 1, visit) ||
                dfs(board, word, row, col + 1, index + 1, visit) ||
                dfs(board, word, row, col - 1, index + 1, visit);
        visit[row][col] = false;
        return sign;

    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        Exist exist = new Exist();
        System.out.println(exist.exist(board, "SEE"));
    }
}
