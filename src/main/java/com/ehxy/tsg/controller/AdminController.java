package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Admin;
import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.BorrowRecord;
import com.ehxy.tsg.entity.FineRecord;
import com.ehxy.tsg.service.AdminService;
import com.ehxy.tsg.service.BookService;
import com.ehxy.tsg.service.BorrowService;
import com.ehxy.tsg.service.FineRecordService;
import com.ehxy.tsg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private FineRecordService fineRecordService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin/dashboard";
    }

    @GetMapping("/list")
    public String listAdmins(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Admin> admins = adminService.selectAll();
        model.addAttribute("admins", admins);
        return "admin/admin_list";
    }

    @GetMapping("/add")
    public String addPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin/admin_add";
    }

    @PostMapping("/add")
    public String addAdmin(Admin admin, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        admin.setStatus(1);
        adminService.insert(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Admin admin = adminService.selectById(id);
        model.addAttribute("admin", admin);
        return "admin/admin_edit";
    }

    @PostMapping("/update")
    public String updateAdmin(Admin admin, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        adminService.update(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        adminService.deleteById(id);
        return "redirect:/admin/list";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/overdue")
    public String overdueList(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<BorrowRecord> overdue = borrowService.selectOverdue();
        model.addAttribute("overdue", overdue);
        return "admin/overdue_list";
    }

    @GetMapping("/statistics")
    public String statistics(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Book> popularBooks = bookService.selectPopularBooks(10);
        model.addAttribute("popularBooks", popularBooks);
        return "admin/statistics";
    }

    @GetMapping("/reviews/manage")
    public String manageReviews(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("reviews", reviewService.selectAll());
        return "admin/review_manage";
    }

    @PostMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        reviewService.deleteById(id);
        return "redirect:/admin/reviews/manage";
    }

    @GetMapping("/fines/manage")
    public String manageFines(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<FineRecord> unpaidFines = fineRecordService.selectUnpaid();
        model.addAttribute("unpaidFines", unpaidFines);
        return "admin/fine_manage";
    }

    @PostMapping("/fines/markPaid/{id}")
    public String markFinePaid(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        fineRecordService.markAsPaid(id);
        return "redirect:/admin/fines/manage";
    }
}
