package offer;

import java.util.Stack;

/**
 * Created by wiyee on 2018/3/16.
 */
public class T42 {
    /**
     * 左旋转字符串
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
     *
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString(String str, int n) {
        if (str.length() < 1 || n < 0)
            return "";
        char[] chars = str.toCharArray();
        char[] tmp = new char[chars.length];
        int k = n % str.length();
        for (int i = 0; i < str.length(); i++) {
            if (i < k) {
                tmp[i + str.length() - k] = chars[i];
            } else
                tmp[i - k] = chars[i];
        }
        return String.valueOf(tmp);
    }

    /**
     * 翻转单词序列
     * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，
     * 有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
     * 正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
     *
     * @param str
     * @return
     */
    public String ReverseSentence(String str) {
        if (str.trim().equals("")) {
            return str;
        }
        String[] paper = str.split(" ");
        Stack<String> stack = new Stack<>();
        for (String i : paper) {
            stack.push(i);
        }
        StringBuffer sb = new StringBuffer();
        while (!stack.empty()) {
            sb.append(stack.pop() + " ");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        T42 t42 = new T42();
        System.out.println(t42.LeftRotateString("XYZdefabc", 3));
        System.out.println(t42.ReverseSentence(" "));
    }
}
