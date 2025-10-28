package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.BookingDTO;
import com.ait.mrb_fp.entity.SeatBooking;

public class BookingMapper {

    public static BookingDTO toDTO(SeatBooking booking) {
        if (booking == null) return null;
        return BookingDTO.builder()
                .allocationId(booking.getAllocationId())
                .seatId(booking.getSeatId())
                .employeeId(booking.getEmployeeId())
                .allocationDate(booking.getAllocationDate())
                .status(booking.getStatus().name())
                .isActive(booking.isActive())
                .build();
    }

    public static SeatBooking toEntity(BookingDTO dto) {
        if (dto == null) return null;
        return SeatBooking.builder()
                .allocationId(dto.getAllocationId())
                .seatId(dto.getSeatId())
                .employeeId(dto.getEmployeeId())
                .allocationDate(dto.getAllocationDate())
                .status(SeatBooking.Status.valueOf(dto.getStatus()))
                .isActive(dto.isActive())
                .build();
    }
}
