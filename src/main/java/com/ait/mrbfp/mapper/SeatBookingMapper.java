package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.SeatBookingRequestDTO;
import com.ait.mrbfp.dto.response.SeatBookingResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.Seat;
import com.ait.mrbfp.entity.SeatBooking;
import com.ait.mrbfp.exception.BadRequestException;

public class SeatBookingMapper {

    private SeatBookingMapper() {
    }

    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        SeatBooking booking = new SeatBooking();
        booking.setSeat(seat);
        booking.setEmployee(employee);
        booking.setAllocationDate(dto.getAllocationDate());
        booking.setActive(true);

        try {
            booking.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid booking status: " + dto.getStatus());
        }

        return booking;
    }

    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {
        SeatBookingResponseDTO dto = new SeatBookingResponseDTO();
        dto.setAllocationId(booking.getAllocationId());
        dto.setSeatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null);
        dto.setEmployeeFirstName(booking.getEmployee() != null ? booking.getEmployee().getFirstName() : null);
        dto.setEmployeeId(booking.getEmployee() != null ? booking.getEmployee().getEmployeeId() : null);
        dto.setAllocationDate(booking.getAllocationDate());
        dto.setStatus(booking.getStatus().name());
        dto.setActive(booking.isActive());
        return dto;
    }
}
