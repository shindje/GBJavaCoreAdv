package hw3;

import java.util.HashMap;
import java.util.Map;

public class Main1 {
    static String[] words = {"a", "b", "abc", "d", "de", "abc", "de", "fgh", "abc", "fgh",
                             "a", "b", "de", "fgh", "abc", "de", "jkl", "lmn", "jkl", "lmn"};

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s: words) {
            int cnt = map.getOrDefault(s, 0);
            map.put(s, cnt + 1);
        }
        System.out.println("Уникальные слова: " + map.keySet());
        System.out.println("Сколько раз встречаются слова: " + map.entrySet());
    }
}
