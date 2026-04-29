package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.BorrowRecord;
import com.ehxy.tsg.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/list")
    public String listBorrows(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<BorrowRecord> records = borrowService.selectAll();
        model.addAttribute("records", records);
        return "admin/borrow_list";
    }

    @GetMapping("/active")
    public String activeBorrows(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<BorrowRecord> records = borrowService.selectActiveBorrows();
        model.addAttribute("records", records);
        return "admin/borrow_active";
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam Long readerId, @RequestParam Long bookId, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        borrowService.borrowBook(readerId, bookId);
        return "redirect:/borrow/list";
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        borrowService.returnBook(id);
        return "redirect:/borrow/list";
    }

    @GetMapping("/search")
    public String searchBorrows(@RequestParam(required = false) Long readerId,
                                @RequestParam(required = false) Long bookId,
                                @RequestParam(required = false) Integer status,
                                Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        BorrowRecord condition = new BorrowRecord();
        condition.setReaderId(readerId);
        condition.setBookId(bookId);
        condition.setStatus(status);
        List<BorrowRecord> records = borrowService.selectByCondition(condition);
        model.addAttribute("records", records);
        return "admin/borrow_list";
    }
}
