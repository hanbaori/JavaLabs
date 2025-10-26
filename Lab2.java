import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Варіант: StringBuffer
// Надрукувати слова без повторень заданого тексту в алфавітному порядку за першою літерою.

public class Lab2 {

    public static void main(String[] args) {
        try {
            StringBuffer text = new StringBuffer("Jack was hungry. He walked to the kitchen. He got out some eggs.");
            List<String> uniqueSortedWords = getUniqueWordsSorted(text);
            System.out.println("Unique words in alphabetical order: " + uniqueSortedWords);
        } catch (Exception e) {
            System.err.println("An error occurred while processing the text: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<String> getUniqueWordsSorted(StringBuffer text) {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        String[] words = text.toString().split("\\s+");
        List<String> uniqueWords = new ArrayList<>();

        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "");
            if (!word.isEmpty() && !containsIgnoreCase(uniqueWords, word)) {
                uniqueWords.add(word);
            }
        }

        Collections.sort(uniqueWords, new Comparator<String>() {
            @Override
            public int compare(String w1, String w2) {
                return Character.compare(Character.toLowerCase(w1.charAt(0)), Character.toLowerCase(w2.charAt(0)));
            }
        });

        return uniqueWords;
    }

    private static boolean containsIgnoreCase(List<String> list, String word) {
        for (String s : list) {
            if (s.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
}
