package offer;

/**
 * Created by wiyee on 2018/4/11.
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
 * 每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
 * 例如 a b c e s f c s a d e e 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，
 * 因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
 */
public class T66 {
    public static void main(String[] args) {
        String string = "ABCESFCSADEE";
        char[] chars = string.toCharArray();
        String string2 = "SEE";
        char[] str = string2.toCharArray();
        System.out.println(hasPath(chars, 3, 4, str));
    }

    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix.length == 0 || matrix == null || str.length == 0 || matrix.length != rows * cols || rows <= 0 || cols <= 0)
            return false;
        boolean[] visited = new boolean[cols * rows];
        int[] pathLength = {0};
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (hasPathCore(matrix, rows, cols, str, i, j, visited, pathLength))
                    return true;
            }
        }
        return false;
    }

    public static boolean hasPathCore(char[] matrix, int rows, int cols, char str[], int row, int col, boolean[] visited, int[] pathLength) {
        boolean flag = false;
        if (row >= 0 && col >= 0 && col < cols && row < rows && !visited[row * cols + col] && matrix[row * cols + col] == str[pathLength[0]]) {
            pathLength[0]++;
            visited[row * cols + col] = true;
            if (pathLength[0] == str.length)
                return true;
            flag = hasPathCore(matrix, rows, cols, str, row, col + 1, visited, pathLength) ||
                    hasPathCore(matrix, rows, cols, str, row, col - 1, visited, pathLength) ||
                    hasPathCore(matrix, rows, cols, str, row + 1, col, visited, pathLength) ||
                    hasPathCore(matrix, rows, cols, str, row - 1, col, visited, pathLength);
            if (!flag) {
                pathLength[0]--;
                visited[row * cols + col] = false;
            }

        }
        return flag;
    }
}