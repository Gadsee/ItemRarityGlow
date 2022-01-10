package me.gadse.itemrarityglow.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegexUtil {
    FILTER_NAME("%filter-name%");

    private final Pattern pattern;

    RegexUtil(String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean matches(String input) {
        return pattern.matcher(input).find();
    }

    public String replaceAll(String input, int replacement)  {
        return replaceAll(input, Integer.toString(replacement));
    }

    public String replaceAll(String input, double replacement)  {
        return replaceAll(input, Double.toString(replacement));
    }

    public String replaceAll(String input, String replacement)  {
        return pattern.matcher(input).replaceAll(Matcher.quoteReplacement(replacement));
    }
}
