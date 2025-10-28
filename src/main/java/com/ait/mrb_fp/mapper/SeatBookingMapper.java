package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.SeatBooking;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.Employee;
import java.time.LocalDateTime;

public class SeatBookingMapper {

    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        return SeatBooking.builder()
                .seat(seat)
                .employee(employee)
                .allocationDate(LocalDateTime.parse(dto.getAllocationDate()))
                .status(SeatBooking.BookingStatus.valueOf(dto.getStatus()))
                .isActive(true)
                .build();
    }

    public static SeatBookingResponseDTO toResponse(SeatBooking entity) {
        return SeatBookingResponseDTO.builder()
                .allocationId(entity.getAllocationId())
                .seatNumber(entity.getSeat().getSeatNumber())
                .employeeName(entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName())
                .allocationDate(entity.getAllocationDate().toString())
                .status(entity.getStatus().name())
                .isActive(entity.isActive())
                .build();
    }
}
