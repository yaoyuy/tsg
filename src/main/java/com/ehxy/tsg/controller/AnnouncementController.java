package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Announcement;
import com.ehxy.tsg.entity.Admin;
import com.ehxy.tsg.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public String listAnnouncements(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Announcement> announcements = announcementService.selectAll();
        model.addAttribute("announcements", announcements);
        return "admin/announcement_list";
    }

    @GetMapping("/add")
    public String addPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin/announcement_add";
    }

    @PostMapping("/add")
    public String addAnnouncement(Announcement announcement, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Admin admin = (Admin) session.getAttribute("admin");
        announcement.setPublisherId(admin.getId());
        announcement.setStatus(1);
        announcementService.insert(announcement);
        return "redirect:/announcement/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        Announcement announcement = announcementService.selectById(id);
        model.addAttribute("announcement", announcement);
        return "admin/announcement_edit";
    }

    @PostMapping("/update")
    public String updateAnnouncement(Announcement announcement, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        announcementService.update(announcement);
        return "redirect:/announcement/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        announcementService.deleteById(id);
        return "redirect:/announcement/list";
    }
}
