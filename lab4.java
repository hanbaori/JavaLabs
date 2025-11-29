import java.util.ArrayList;
import java.util.List;

class Letter {
    private final char value;
    public Letter(char value) { this.value = value; }
    public char getValue() { return value; }
    @Override
    public String toString() { return String.valueOf(value); }
}

class Punctuation {
    private final char value;
    public Punctuation(char value) { this.value = value; }
    public char getValue() { return value; }
    @Override
    public String toString() { return String.valueOf(value); }
}

class Word {
    private final Letter[] letters;
    public Word(String text) {
        letters = new Letter[text.length()];
        for (int i = 0; i < text.length(); i++) letters[i] = new Letter(text.charAt(i));
    }
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Letter l : letters) sb.append(l.getValue());
        return sb.toString();
    }
    @Override
    public String toString() { return getText(); }
}

class Sentence {
    private final List<Object> elements = new ArrayList<>();
    public Sentence(String s) {
        String[] tokens = s.trim().split("(?=[.,!?])|\\s+");
        for (String t : tokens) {
            if (t.isEmpty()) continue;
            if (t.matches("[.,!?]")) elements.add(new Punctuation(t.charAt(0)));
            else elements.add(new Word(t));
        }
    }
    public List<Object> getElements() { return elements; }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object o : elements) {
            if (o instanceof Word word) sb.append(word.getText()).append(" ");
            else if (o instanceof Punctuation p) sb.append(p.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
}

class Text {
    private final Sentence[] sentences;
    public Text(String input) {
        String cleaned = input.replaceAll("[ \t]+", " ").trim();
        String[] arr = cleaned.split("(?<=[.!?])");
        List<Sentence> list = new ArrayList<>();
        for (String s : arr) if (!s.trim().isEmpty()) list.add(new Sentence(s.trim()));
        sentences = list.toArray(Sentence[]::new);
    }
    public Sentence[] getSentences() { return sentences; }
}

public class lab4 {

    public static void main(String[] args) {
        String raw = "Jack was hungry.\tHe walked to the kitchen. He got out some eggs.";
        Text text = new Text(raw);

        List<String> unique = extractUniqueWords(text);

        unique.sort((a, b) -> Character.compare(Character.toLowerCase(a.charAt(0)),
                                                Character.toLowerCase(b.charAt(0))));

        System.out.println("Unique words:");
        System.out.println(unique);
    }

    private static List<String> extractUniqueWords(Text text) {
        List<String> list = new ArrayList<>();
        for (Sentence s : text.getSentences()) {
            for (Object e : s.getElements()) {
                if (e instanceof Word word) {
                    String w = word.getText();
                    if (!containsIgnoreCase(list, w)) list.add(w);
                }
            }
        }
        return list;
    }

    private static boolean containsIgnoreCase(List<String> list, String w) {
        for (String s : list) if (s.equalsIgnoreCase(w)) return true;
        return false;
    }
}
