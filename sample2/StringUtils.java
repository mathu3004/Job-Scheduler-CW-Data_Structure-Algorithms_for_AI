package Coursework.sample2;

public class StringUtils {
    // Capitalize the first letter of a string
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Capitalize the first letter of each sentence in a string
    public static String capitalizeSentences(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] sentences = str.split("(?<=[.!?])\\s*");
        StringBuilder capitalized = new StringBuilder();
        for (String sentence : sentences) {
            capitalized.append(capitalizeFirstLetter(sentence.trim())).append(" ");
        }
        return capitalized.toString().trim();
    }
}
