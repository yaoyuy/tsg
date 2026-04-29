package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.BorrowRecord;
import com.ehxy.tsg.entity.Reservation;
import com.ehxy.tsg.entity.Favorite;
import com.ehxy.tsg.entity.Review;
import com.ehxy.tsg.entity.Message;
import com.ehxy.tsg.entity.FineRecord;
import com.ehxy.tsg.service.ReaderService;
import com.ehxy.tsg.service.BookService;
import com.ehxy.tsg.service.BorrowService;
import com.ehxy.tsg.service.ReservationService;
import com.ehxy.tsg.service.FavoriteService;
import com.ehxy.tsg.service.ReviewService;
import com.ehxy.tsg.service.MessageService;
import com.ehxy.tsg.service.FineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private FineRecordService fineRecordService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String readerNo, @RequestParam String password, HttpSession session, Model model) {
        Reader reader = readerService.login(readerNo, password);
        if (reader != null) {
            session.setAttribute("user", reader);
            return "redirect:/user/dashboard";
        }
        model.addAttribute("error", "读者号或密码错误");
        return "user/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(Reader reader, Model model) {
        Reader existReader = readerService.selectByReaderNo(reader.getReaderNo());
        if (existReader != null) {
            model.addAttribute("error", "读者号已存在");
            return "user/register";
        }
        readerService.register(reader);
        return "redirect:/user/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<BorrowRecord> activeBorrows = borrowService.selectByReaderId(reader.getId());
        List<Reservation> reservations = reservationService.selectByReaderId(reader.getId());
        int unreadCount = messageService.countUnreadByReaderId(reader.getId());
        BigDecimal unpaidFine = fineRecordService.sumUnpaidByReaderId(reader.getId());
        model.addAttribute("reader", reader);
        model.addAttribute("activeBorrows", activeBorrows);
        model.addAttribute("reservations", reservations);
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("unpaidFine", unpaidFine != null ? unpaidFine : BigDecimal.ZERO);
        model.addAttribute("user", reader);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        model.addAttribute("reader", reader);
        model.addAttribute("user", reader);
        return "user/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(Reader reader, HttpSession session) {
        Reader currentUser = (Reader) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        reader.setId(currentUser.getId());
        reader.setReaderNo(currentUser.getReaderNo());
        reader.setPassword(currentUser.getPassword());
        readerService.update(reader);
        session.setAttribute("user", reader);
        return "redirect:/user/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session, Model model) {
        Reader currentUser = (Reader) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        if (!currentUser.getPassword().equals(oldPassword)) {
            model.addAttribute("error", "原密码错误");
            model.addAttribute("reader", currentUser);
            model.addAttribute("user", currentUser);
            return "user/profile";
        }
        readerService.updatePassword(currentUser.getId(), newPassword);
        currentUser.setPassword(newPassword);
        session.setAttribute("user", currentUser);
        model.addAttribute("success", "密码修改成功");
        model.addAttribute("reader", currentUser);
        model.addAttribute("user", currentUser);
        return "user/profile";
    }

    @GetMapping("/borrowHistory")
    public String borrowHistory(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<BorrowRecord> records = borrowService.selectByReaderId(reader.getId());
        model.addAttribute("records", records);
        model.addAttribute("user", reader);
        return "user/borrow_history";
    }

    @PostMapping("/renew/{borrowId}")
    public String renewBook(@PathVariable Long borrowId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        borrowService.renewBook(borrowId);
        messageService.sendMessage(reader.getId(), "续借成功", "您的图书已成功续借30天");
        return "redirect:/user/borrowHistory";
    }

    @GetMapping("/reservations")
    public String reservations(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<Reservation> reservations = reservationService.selectByReaderId(reader.getId());
        model.addAttribute("reservations", reservations);
        model.addAttribute("user", reader);
        return "user/reservations";
    }

    @PostMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable Long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        borrowService.borrowBook(reader.getId(), bookId);
        messageService.sendMessage(reader.getId(), "借阅成功", "您已成功借阅图书，请在30天内归还");
        return "redirect:/user/borrowHistory";
    }

    @PostMapping("/reserve/{bookId}")
    public String reserveBook(@PathVariable Long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        Reservation reservation = new Reservation();
        reservation.setReaderId(reader.getId());
        reservation.setBookId(bookId);
        reservationService.insert(reservation);
        messageService.sendMessage(reader.getId(), "预约成功", "您已成功预约图书，请在3天内到馆借阅");
        return "redirect:/user/reservations";
    }

    @PostMapping("/cancelReservation/{id}")
    public String cancelReservation(@PathVariable Long id, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        reservationService.cancelReservation(id);
        return "redirect:/user/reservations";
    }

    @GetMapping("/favorites")
    public String favorites(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<Favorite> favorites = favoriteService.selectByReaderId(reader.getId());
        model.addAttribute("favorites", favorites);
        model.addAttribute("user", reader);
        return "user/favorites";
    }

    @PostMapping("/toggleFavorite/{bookId}")
    public String toggleFavorite(@PathVariable Long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        favoriteService.toggleFavorite(reader.getId(), bookId);
        return "redirect:/book/detail/" + bookId;
    }

    @GetMapping("/reviews")
    public String reviews(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<Review> reviews = reviewService.selectByReaderId(reader.getId());
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", reader);
        return "user/reviews";
    }

    @PostMapping("/addReview")
    public String addReview(@RequestParam Long bookId, @RequestParam Integer rating, @RequestParam String content, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        reviewService.addOrUpdateReview(reader.getId(), bookId, rating, content);
        return "redirect:/book/detail/" + bookId;
    }

    @GetMapping("/messages")
    public String messages(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<Message> messages = messageService.selectByReaderId(reader.getId());
        model.addAttribute("messages", messages);
        messageService.markAllAsRead(reader.getId());
        model.addAttribute("user", reader);
        return "user/messages";
    }

    @GetMapping("/fines")
    public String fines(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        List<FineRecord> fines = fineRecordService.selectByReaderId(reader.getId());
        BigDecimal unpaidFine = fineRecordService.sumUnpaidByReaderId(reader.getId());
        model.addAttribute("fines", fines);
        model.addAttribute("unpaidFine", unpaidFine != null ? unpaidFine : BigDecimal.ZERO);
        model.addAttribute("user", reader);
        return "user/fines";
    }

    @PostMapping("/payFine/{id}")
    public String payFine(@PathVariable Long id, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            return "redirect:/login";
        }
        fineRecordService.markAsPaid(id);
        return "redirect:/user/fines";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
