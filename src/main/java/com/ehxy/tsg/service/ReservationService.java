package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Reservation;
import com.ehxy.tsg.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;

    public Reservation selectById(Long id) {
        return reservationMapper.selectById(id);
    }

    public List<Reservation> selectByReaderId(Long readerId) {
        return reservationMapper.selectByReaderId(readerId);
    }

    public List<Reservation> selectActiveReservations() {
        return reservationMapper.selectActiveReservations();
    }

    public List<Reservation> selectAll() {
        return reservationMapper.selectAll();
    }

    public int insert(Reservation reservation) {
        int count = reservationMapper.countActiveByReaderAndBook(reservation.getReaderId(), reservation.getBookId());
        if (count > 0) {
            return -1;
        }
        reservation.setReserveDate(LocalDateTime.now());
        reservation.setExpireDate(LocalDateTime.now().plusDays(7));
        reservation.setStatus(0);
        return reservationMapper.insert(reservation);
    }

    public int cancelReservation(Long id) {
        Reservation reservation = reservationMapper.selectById(id);
        if (reservation != null) {
            reservation.setStatus(2);
            return reservationMapper.update(reservation);
        }
        return -1;
    }
}
