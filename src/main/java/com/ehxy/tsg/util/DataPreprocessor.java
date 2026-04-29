package com.ehxy.tsg.util;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataPreprocessor {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "的", "了", "在", "是", "我", "有", "和", "就", "不", "人", "都", "一", "一个", "上", "也", "很", "到", "说", "要",
        "去", "你", "会", "着", "没有", "看", "好", "自己", "这", "那", "里", "啊", "呢", "吧", "吗", "什么", "怎么", "为什么",
        "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by", "is", "are", "was", "were"
    ));

    public static List<String> deduplicate(List<String> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static String fillMissingValue(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value;
    }

    public static Integer fillMissingValue(Integer value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static Double fillMissingValue(Double value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static List<String> filterOutliers(List<Double> data, double threshold) {
        if (data == null || data.size() < 2) {
            return data != null ? data.stream().map(String::valueOf).collect(Collectors.toList()) : new ArrayList<>();
        }
        
        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = data.stream().mapToDouble(d -> Math.pow(d - mean, 2)).average().orElse(0);
        double stdDev = Math.sqrt(variance);
        
        double lowerBound = mean - threshold * stdDev;
        double upperBound = mean + threshold * stdDev;
        
        return data.stream()
                .filter(d -> d >= lowerBound && d <= upperBound)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public static List<String> tokenize(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> tokens = new ArrayList<>();
        
        String englishPattern = "[a-zA-Z]+";
        String[] parts = text.split("\\s+");
        
        for (String part : parts) {
            if (Pattern.matches(englishPattern, part)) {
                tokens.add(part.toLowerCase());
            } else {
                for (char c : part.toCharArray()) {
                    if (Character.isLetterOrDigit(c)) {
                        tokens.add(String.valueOf(c));
                    }
                }
            }
        }
        
        return tokens;
    }

    public static List<String> filterStopWords(List<String> tokens) {
        if (tokens == null) {
            return new ArrayList<>();
        }
        return tokens.stream()
                .filter(token -> !STOP_WORDS.contains(token.toLowerCase()))
                .filter(token -> token.length() > 1 || Character.isLetterOrDigit(token.charAt(0)))
                .collect(Collectors.toList());
    }

    public static Map<String, Integer> extractKeywords(String text, int topN) {
        if (text == null || text.isEmpty()) {
            return new HashMap<>();
        }
        
        List<String> tokens = tokenize(text);
        tokens = filterStopWords(tokens);
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String token : tokens) {
            frequencyMap.put(token, frequencyMap.getOrDefault(token, 0) + 1);
        }
        
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static String preprocessBookDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "暂无描述";
        }
        
        description = description.trim();
        
        if (description.length() > 500) {
            description = description.substring(0, 500) + "...";
        }
        
        return description;
    }

    public static String extractKeywordsAsString(String text, int topN) {
        Map<String, Integer> keywords = extractKeywords(text, topN);
        return String.join(",", keywords.keySet());
    }
}
