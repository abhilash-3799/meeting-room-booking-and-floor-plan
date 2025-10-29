package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.*;

public class SeatBookingMapper {

    private SeatBookingMapper() {}

    public static SeatBooking toEntity(SeatBookingRequestDTO r, Seat seat, Employee emp) {
        SeatBooking s = new SeatBooking();
        s.setSeat(seat);
        s.setEmployee(emp);
        s.setAllocationDate(r.getAllocationDate());
        s.setStatus(SeatBooking.BookingStatus.valueOf(r.getStatus()));
        s.setActive(true);
        return s;
    }

    public static SeatBookingResponseDTO toResponse(SeatBooking s) {
        SeatBookingResponseDTO r = new SeatBookingResponseDTO();
        r.setAllocationId(s.getAllocationId());
        r.setSeatNumber(s.getSeat() != null ? s.getSeat().getSeatNumber() : null);
        r.setEmployeeName(s.getEmployee() != null ? s.getEmployee().getFirstName() + " " + s.getEmployee().getLastName() : null);
        r.setAllocationDate(s.getAllocationDate());
        r.setStatus(s.getStatus().name());
        r.setActive(s.isActive());
        return r;
    }

    public static void updateEntity(SeatBooking s, SeatBookingRequestDTO r, Seat seat, Employee emp) {
        s.setSeat(seat);
        s.setEmployee(emp);
        s.setAllocationDate(r.getAllocationDate());
        s.setStatus(SeatBooking.BookingStatus.valueOf(r.getStatus()));
    }
}
