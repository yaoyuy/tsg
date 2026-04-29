package com.ehxy.tsg.controller;

import com.ehxy.tsg.util.DataPreprocessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/preprocess")
public class DataPreprocessController {

    @GetMapping("/page")
    public String preprocessPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin/preprocess";
    }

    @PostMapping("/deduplicate")
    @ResponseBody
    public Map<String, Object> deduplicate(@RequestParam String data) {
        List<String> list = Arrays.asList(data.split(","));
        List<String> result = DataPreprocessor.deduplicate(list);
        Map<String, Object> response = new HashMap<>();
        response.put("original", list);
        response.put("result", result);
        response.put("originalCount", list.size());
        response.put("resultCount", result.size());
        return response;
    }

    @PostMapping("/tokenize")
    @ResponseBody
    public Map<String, Object> tokenize(@RequestParam String text) {
        List<String> tokens = DataPreprocessor.tokenize(text);
        List<String> filtered = DataPreprocessor.filterStopWords(tokens);
        Map<String, Integer> keywords = DataPreprocessor.extractKeywords(text, 10);
        
        Map<String, Object> response = new HashMap<>();
        response.put("tokens", tokens);
        response.put("filteredTokens", filtered);
        response.put("keywords", keywords);
        return response;
    }

    @PostMapping("/keywords")
    @ResponseBody
    public Map<String, Object> extractKeywords(@RequestParam String text, 
                                                @RequestParam(defaultValue = "10") int topN) {
        Map<String, Integer> keywords = DataPreprocessor.extractKeywords(text, topN);
        Map<String, Object> response = new HashMap<>();
        response.put("keywords", keywords);
        return response;
    }

    @PostMapping("/filter-outliers")
    @ResponseBody
    public Map<String, Object> filterOutliers(@RequestParam String data,
                                               @RequestParam(defaultValue = "2") double threshold) {
        List<String> parts = Arrays.asList(data.split(","));
        List<Double> numbers = new ArrayList<>();
        for (String part : parts) {
            try {
                numbers.add(Double.parseDouble(part.trim()));
            } catch (NumberFormatException e) {
            }
        }
        List<String> result = DataPreprocessor.filterOutliers(numbers, threshold);
        Map<String, Object> response = new HashMap<>();
        response.put("original", numbers);
        response.put("result", result);
        return response;
    }
}
