package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.BookCategory;
import com.ehxy.tsg.entity.BookDetail;
import com.ehxy.tsg.entity.BookTag;
import com.ehxy.tsg.entity.Review;
import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.service.BookService;
import com.ehxy.tsg.service.TagService;
import com.ehxy.tsg.service.ReviewService;
import com.ehxy.tsg.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    public String listBooks(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Book> books = bookService.selectAll();
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        return "admin/book_list";
    }

    @GetMapping("/add")
    public String addPage(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("book", new Book());
        model.addAttribute("detail", new BookDetail());
        return "admin/book_add";
    }

    @PostMapping("/add")
    public String addBook(Book book, @ModelAttribute BookDetail detail, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        book.setDetail(detail);
        bookService.insert(book);
        return "redirect:/book/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Book book = bookService.selectById(id);
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("book", book);
        model.addAttribute("detail", book.getDetail());
        model.addAttribute("categories", categories);
        return "admin/book_edit";
    }

    @PostMapping("/update")
    public String updateBook(Book book, @ModelAttribute BookDetail detail, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        book.setDetail(detail);
        bookService.update(book);
        return "redirect:/book/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        bookService.deleteById(id);
        return "redirect:/book/list";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) Long categoryId,
                              Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Book condition = new Book();
        condition.setTitle(title);
        condition.setAuthor(author);
        condition.setCategoryId(categoryId);
        List<Book> books = bookService.selectByCondition(condition);
        List<BookCategory> categories = bookService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        return "admin/book_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        Book book = bookService.selectById(id);
        List<BookTag> tags = tagService.getTagsByBookId(id);
        List<Review> reviews = reviewService.selectByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("tags", tags);
        model.addAttribute("reviews", reviews);
        Reader user = (Reader) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("isFavorite", favoriteService.isFavorite(user.getId(), id));
            model.addAttribute("user", user);
        }
        return "book_detail";
    }
}
