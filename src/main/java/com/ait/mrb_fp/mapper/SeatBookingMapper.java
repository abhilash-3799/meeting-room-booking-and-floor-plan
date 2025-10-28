package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.SeatBookingDTO;
import com.ait.mrb_fp.entity.*;

public class SeatBookingMapper {

    public static SeatBookingDTO toDTO(SeatBooking booking) {
        if (booking == null) return null;

        return SeatBookingDTO.builder()
                .allocationId(booking.getAllocationId())
                .seatId(booking.getSeat() != null ? booking.getSeat().getSeatId() : null)
                .seatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null)
                .employeeId(booking.getEmployee() != null ? booking.getEmployee().getEmployeeId() : null)
                .employeeName(booking.getEmployee() != null
                        ? booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName()
                        : null)
                .allocationDate(booking.getAllocationDate())
                .status(booking.getStatus().name())
                .isActive(booking.isActive())
                .build();
    }

    public static SeatBooking toEntity(SeatBookingDTO dto) {
        if (dto == null) return null;

        Seat seat = dto.getSeatId() != null ? Seat.builder().seatId(dto.getSeatId()).build() : null;
        Employee employee = dto.getEmployeeId() != null ? Employee.builder().employeeId(dto.getEmployeeId()).build() : null;

        return SeatBooking.builder()
                .allocationId(dto.getAllocationId())
                .seat(seat)
                .employee(employee)
                .allocationDate(dto.getAllocationDate())
                .status(SeatBooking.BookingStatus.valueOf(dto.getStatus()))
                .isActive(dto.isActive())
                .build();
    }
}
