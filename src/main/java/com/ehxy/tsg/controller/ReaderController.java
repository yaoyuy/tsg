package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/list")
    public String listReaders(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Reader> readers = readerService.selectAll();
        model.addAttribute("readers", readers);
        return "admin/reader_list";
    }

    @GetMapping("/add")
    public String addPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin/reader_add";
    }

    @PostMapping("/add")
    public String addReader(Reader reader, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        reader.setStatus(1);
        readerService.insert(reader);
        return "redirect:/reader/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Reader reader = readerService.selectById(id);
        model.addAttribute("reader", reader);
        return "admin/reader_edit";
    }

    @PostMapping("/update")
    public String updateReader(Reader reader, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        readerService.update(reader);
        return "redirect:/reader/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteReader(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        readerService.deleteById(id);
        return "redirect:/reader/list";
    }

    @GetMapping("/search")
    public String searchReaders(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String department,
                                Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Reader condition = new Reader();
        condition.setName(name);
        condition.setDepartment(department);
        List<Reader> readers = readerService.selectByCondition(condition);
        model.addAttribute("readers", readers);
        return "admin/reader_list";
    }
}
