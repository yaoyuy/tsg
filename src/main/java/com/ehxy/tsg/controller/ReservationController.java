package com.ehxy.tsg.controller;

import com.ehxy.tsg.entity.Reservation;
import com.ehxy.tsg.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/list")
    public String listReservations(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Reservation> reservations = reservationService.selectAll();
        model.addAttribute("reservations", reservations);
        return "admin/reservation_list";
    }

    @PostMapping("/add")
    public String addReservation(Reservation reservation, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        reservationService.insert(reservation);
        return "redirect:/reservation/list";
    }

    @GetMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        reservationService.cancelReservation(id);
        return "redirect:/reservation/list";
    }
}
