package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.BookCategory;
import com.ehxy.tsg.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String listCategories(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/category_list";
    }

    @PostMapping("/add")
    public String addCategory(BookCategory category, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        bookService.getAllCategories().add(category);
        return "redirect:/category/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "redirect:/category/list";
    }
}
