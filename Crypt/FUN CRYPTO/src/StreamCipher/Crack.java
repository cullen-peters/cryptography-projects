package StreamCipher;
import java.util.*;
public class Crack {
    static String url = "dictionary.txt";
    static In page = new In(url);
    static String html = page.readAll();
    static String[] dictionary = html.split("\n");
    static List<String> dict = Arrays.asList(dictionary);
    public static void main(String[] args) {
        String[] m = args;
        for (String word : m) {
            System.out.println(word);
        }
        System.out.println((getEnglishCount(m) * 100));
    }
    public static double getEnglishCount(String[] m) {
        double matches = 0;
        
        for (String word : m) {
            if (dict.contains(word.toUpperCase()))
                matches++;
        }
        System.out.println("matches "+matches);
        System.out.println(dict.contains(m[0].toUpperCase()));
        return matches/m.length;
    }
}