import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordCounter {
    private static final String STOP_WORDS_FILE = "stopwords.txt"; // File containing common words to ignore
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter '1' to input text manually, or '2' to provide a file:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        String inputText = "";
        if (choice == 1) {
            System.out.println("Enter the text:");
            inputText = scanner.nextLine();
        } else if (choice == 2) {
            System.out.println("Enter the file path:");
            String filePath = scanner.nextLine();
            try {
                inputText = readFile(filePath);
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
                System.exit(1);
            }
        } else {
            System.err.println("Invalid choice. Exiting...");
            System.exit(1);
        }
        
        // Split the input text into an array of words using space and punctuation as delimiters
        String[] words = inputText.split("[\\s\\p{Punct}]+");
        
        // Initialize a counter variable to keep track of the number of words
        int wordCount = words.length;
        
        // Ignore common words or stop words
        try {
            String[] stopWords = readFile(STOP_WORDS_FILE).split("[\\s\\p{Punct}]+");
            wordCount = countWordsWithoutStopWords(words, stopWords);
        } catch (IOException e) {
            System.err.println("Error reading stop words file: " + e.getMessage());
        }
        
        System.out.println("Total word count: " + wordCount);
        
        // Additional feature: Display the number of unique words and their frequency
        displayWordFrequency(words);
    }
    
    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    private static int countWordsWithoutStopWords(String[] words, String[] stopWords) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        
        for (String word : words) {
            String lowercaseWord = word.toLowerCase();
            if (!isStopWord(lowercaseWord, stopWords)) {
                wordFrequency.put(lowercaseWord, wordFrequency.getOrDefault(lowercaseWord, 0) + 1);
            }
        }
        
        return wordFrequency.size();
    }
    
    private static boolean isStopWord(String word, String[] stopWords) {
        for (String stopWord : stopWords) {
            if (word.equals(stopWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    private static void displayWordFrequency(String[] words) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            String lowercaseWord = word.toLowerCase();
            wordFrequency.put(lowercaseWord, wordFrequency.getOrDefault(lowercaseWord, 0) + 1);
        }
        
        System.out.println("Number of unique words: " + wordFrequency.size());
        System.out.println("Word Frequency:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}


//C:\Users\Dell\OneDrive\JAVA_TASK\stopwords.txt  file path