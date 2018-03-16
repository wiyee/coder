package offer;

import java.util.HashMap;

/**
 * Created by wiyee on 2018/3/6.
 */
public class T55 {
    public int FirstNotRepeatingChar(String str) {
        int result = -1;
        if (str.trim().length() > 10000 || str.trim().length() < 1)
            return result;
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (hashMap.containsKey(str.charAt(i))) {
                int tmpInt = hashMap.get(str.charAt(i));
                hashMap.put(str.charAt(i), tmpInt + 1);
            } else
                hashMap.put(str.charAt(i), 1);
        }
        System.out.println(hashMap);
        for (int i = 0; i < str.length(); i++) {
            if (hashMap.get(str.charAt(i)) == 1)
                return i;
        }
        return result;
    }

    public static void main(String[] args) {
        int i = new T55().FirstNotRepeatingChar("google");
        System.out.println(i);
    }
}
