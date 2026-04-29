package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Admin;
import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.Announcement;
import com.ehxy.tsg.entity.BookCategory;
import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.service.BookService;
import com.ehxy.tsg.service.AnnouncementService;
import com.ehxy.tsg.service.BorrowService;
import com.ehxy.tsg.service.ReaderService;
import com.ehxy.tsg.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/home")
    public String index(Model model, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<Book> latestBooks = bookService.selectLatestBooks(10);
        List<Announcement> announcements = announcementService.selectActiveAnnouncements();
        List<BookCategory> categories = bookService.getAllCategories();
        
        model.addAttribute("latestBooks", latestBooks);
        model.addAttribute("announcements", announcements);
        model.addAttribute("categories", categories);
        model.addAttribute("totalBooks", bookService.countAll());
        model.addAttribute("availableBooks", bookService.countAvailable());
        model.addAttribute("totalReaders", readerService.countActiveReaders());
        model.addAttribute("totalBorrows", borrowService.countAll());
        model.addAttribute("user", reader);
        return "index";
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String loginType,
                        @RequestParam(required = false) String readerNo,
                        @RequestParam(required = false) String username,
                        @RequestParam String password,
                        Model model, HttpSession session) {
        if ("admin".equals(loginType)) {
            Admin admin = adminService.login(username, password);
            if (admin != null) {
                session.setAttribute("admin", admin);
                return "redirect:/admin/dashboard";
            } else {
                model.addAttribute("error", "用户名或密码错误");
                return "login";
            }
        } else {
            Reader reader = readerService.login(readerNo, password);
            if (reader != null) {
                session.setAttribute("user", reader);
                return "redirect:/home";
            } else {
                model.addAttribute("error", "读者号或密码错误");
                return "login";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) Long categoryId,
                              Model model, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        Book condition = new Book();
        condition.setTitle(keyword);
        condition.setAuthor(keyword);
        condition.setCategoryId(categoryId);
        List<Book> books = bookService.selectByCondition(condition);
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("user", reader);
        return "search_result";
    }
}
