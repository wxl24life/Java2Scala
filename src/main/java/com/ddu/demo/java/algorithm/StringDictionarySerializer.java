package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * === Dictionary Serializer ===
 * text = store(a); // translate an array to string text
 * a = load(text);  // translate back from a string text to an array
 *
 * Note: We assume the `load` method is allways called after the store method
 *
 * @author wangxiaolong
 */
public class StringDictionarySerializer {

    private static Map<Character, String> SPLITTERS = new HashMap<>();
    private static Map<String, Character> REVERSED_SPLITTERS = new HashMap<>();

    static {
        SPLITTERS.put('%', "%A");
        SPLITTERS.put('=', "%B");
        SPLITTERS.put(';', "%C");
        SPLITTERS.put('\n', "%D");
        REVERSED_SPLITTERS.put("%A", '%');
        REVERSED_SPLITTERS.put("%B", '=');
        REVERSED_SPLITTERS.put("%C", ';');
        REVERSED_SPLITTERS.put("%D", '\n');
    }

    /**
     * Use '%' as the Escape character
     * @param array
     * @return the string after translate
     */
    public String store(Map[] array) {
        StringBuilder sb = new StringBuilder();
        boolean isFirstMap = true;
        for (Map map : array) {
            if (isFirstMap) {
                isFirstMap = false;
            } else {
                sb.append("\n");
            }
            boolean isFirstKeyOfTheMap = true;
            for (Object key : map.keySet()) {
                if (isFirstKeyOfTheMap) {
                    isFirstKeyOfTheMap = false;
                } else {
                    sb.append(";");
                }
                String value = (String) map.get(key);
                sb.append(encode((String) key));
                sb.append("=");
                sb.append(encode(value));
            }
        }
        return sb.toString();
    }

    /**
     * translate the separator as follows:
     *      %  => %A
     *      =  => %B
     *      ;  => %C
     *      \n => %D
     */
    private String encode(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (SPLITTERS.containsKey(c)) {
                sb.append(SPLITTERS.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * translate the text string to map
     * @param text
     * @return
     */
    public Map[] load(String text) {
        if (text == null || text.isEmpty()) return null;

        List<Map<String, String>> resultList = new ArrayList<>();

        char[] chars = text.toCharArray();
        int charsLength = chars.length;

        char c;
        int i = 0;

        StringBuilder sb = new StringBuilder();
        String key = null;
        Map<String, String> currentMap = new HashMap<>();
        while (i < charsLength) {
            c = chars[i];
            switch (c) {
                case '%':
                    if (i + 1 < charsLength) {
                        String reversedKey = new String(chars, i, 2);
                        if (REVERSED_SPLITTERS.containsKey(reversedKey)) {
                            sb.append(REVERSED_SPLITTERS.get(reversedKey));
                            i++;
                            break;
                        }
                    }
                    throw new IllegalArgumentException("Found invalid input argument. " + text);
                case '=':
                    key = sb.toString();
                    sb = new StringBuilder();
                    break;
                case ';':
                    currentMap.put(key, sb.toString());
                    sb = new StringBuilder();
                    break;
                case '\n':
                    currentMap.put(key, sb.toString());
                    // add this currentMap as an array element
                    resultList.add(currentMap);
                    // reset currentMap
                    currentMap = new HashMap<>();
                    // reset sb
                    sb = new StringBuilder();
                    break;
                default:
                    sb.append(c);
                    break;
            }
            i++;
        }
        currentMap.put(key, sb.toString());
        resultList.add(currentMap);
        return resultList.toArray(new Map[resultList.size()]);
    }

    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1%=v1;k2=v2\nk3=v3", "v1");
        map1.put("k4", "v4");
        Map<String, String> map2 = new HashMap<>();
        map2.put("A", "XXX");
        Map<String, String> map3 = new HashMap<>();
        map3.put("", "YYY");
        map3.put("ZZZ", "");
        Map[] array = new Map[]{ map1, map2, map3 };

        StringDictionarySerializer serializer = new StringDictionarySerializer();
        String text = serializer.store(array);
        System.out.println(text);
        System.out.println("====================");
        Map[] decodedMap = serializer.load(text);
        for (Map map : decodedMap) {
            System.out.println(map);
        }
        System.out.println("====================");
        System.out.println(Arrays.toString(serializer.load("k1=v1;k2=v2\nA=XXX")));
    }
}
