package leetcode;

import java.util.*;

/**
 * Created by wiyee on 2018/5/3.
 * Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
 * <p>
 * Only one letter can be changed at a time
 * Each intermediate word must exist in the dictionary
 * For example,
 * <p>
 * Given:
 * start ="hit"
 * end ="cog"
 * dict =["hot","dot","dog","lot","log"]
 * <p>
 * As one shortest transformation is"hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length5.
 * <p>
 * Note:
 * <p>
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * <p>
 * bfs
 */
public class LadderLength {

    public int ladderLength(String start, String end, HashSet<String> dict) {
        int result = 0;
        LinkedList<String> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                String tmp = queue.poll();

                size--;
                if (compare(end, tmp)) {
                    return result + 1;
                }
                for (Iterator<String> it = dict.iterator(); it.hasNext(); ) {
                    String str = it.next();
                    if (compare(str, start)) {
                        queue.offer(str);
                        it.remove();
                    }
                }
            }
            result++;
        }
        return 0;
    }

    public static boolean compare(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
            }
        }
        return count == 1 ? true : false;
    }

    /**
     * 看错题目。。遍历顺序为set中的连续字符
     * dfs
     * public static int minLength = Integer.MAX_VALUE;
     * public int ladderLength(String start, String end, HashSet<String> dict) {
     * if (start.length() != end.length())
     * return -1;
     * <p>
     * dfs(start,end,dict,new ArrayList<>());
     * <p>
     * if (minLength != Integer.MAX_VALUE)
     * return minLength;
     * else
     * return -1;
     * }
     * public static void dfs(String start, String end, HashSet<String> dict, ArrayList<String> list){
     * if (compare(start,end)){
     * System.out.println(list);
     * if (list.size() + 1 < minLength)
     * minLength = list.size() + 1;
     * }
     * for (String tmp:dict){
     * if (compare(start,tmp) && ! list.contains(tmp)){
     * list.add(tmp);
     * dfs(tmp,end,dict,list);
     * list.remove(list.size() - 1);
     * }
     * }
     * }
     * <p>
     * public static boolean compare(String s1, String s2){
     * int count = 0;
     * for (int i = 0; i < s1.length(); i++){
     * if (s1.charAt(i) != s2.charAt(i)){
     * count ++;
     * }
     * }
     * if (count ==1)
     * return true;
     * else
     * return false;
     * }
     **/
    public static void main(String[] args) {
        LadderLength ladderLength = new LadderLength();
        HashSet<String> set = new HashSet<>();
        set.add("hot");
        set.add("dot");
        set.add("dog");
        set.add("lot");
        set.add("log");
        System.out.println(ladderLength.ladderLength("hit", "cog", set));
    }
}
