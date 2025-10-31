//package com.ait.mrb_fp.mapper;
//
//import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
//import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
//import com.ait.mrb_fp.entity.Employee;
//import com.ait.mrb_fp.entity.Seat;
//import com.ait.mrb_fp.entity.SeatBooking;
//
//public class SeatBookingMapper {
//
//    private SeatBookingMapper() {}
//
//    // ✅ Convert RequestDTO to Entity
//    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
//        SeatBooking booking = new SeatBooking();
//        booking.setSeat(seat);
//        booking.setEmployee(employee);
//      booking.setAllocationDate(dto.getAllocationDate());
//
//
//        booking.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));
//        booking.setActive(true);
//        return booking;
//    }
//
//    // ✅ Convert Entity to ResponseDTO
//    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {
//        if (booking == null) return null;
//
//        return SeatBookingResponseDTO.builder()
//                .allocationId(booking.getAllocationId())
//                .seatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null)
//                .employeeName(booking.getEmployee() != null
//                        ? booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName()
//                        : null)
//                .allocationDate(booking.getAllocationDate())
//                .status(booking.getStatus() != null ? booking.getStatus().name() : null)
//                .isActive(booking.isActive())
//                .build();
//    }
//
//    // ✅ Update existing booking from RequestDTO
//    public static void updateEntity(SeatBooking existing, SeatBookingRequestDTO dto, Seat seat, Employee employee) {
//        existing.setSeat(seat);
//        existing.setEmployee(employee);
//        existing.setAllocationDate(dto.getAllocationDate());
//        existing.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));
//    }
//}

package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.SeatBooking;

import java.time.LocalDateTime;

public class SeatBookingMapper {

    private SeatBookingMapper() {}

    // ✅ Convert RequestDTO to Entity
    public static SeatBooking toEntity(SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        SeatBooking booking = new SeatBooking();
        booking.setSeat(seat);
        booking.setEmployee(employee);

        // ✅ Always set current system time for allocationDate
        booking.setAllocationDate(LocalDateTime.now());

        // ✅ Set status and mark active
        booking.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));
        booking.setActive(true);

        // ✅ Generate unique allocation ID
        booking.setAllocationId("BK" + System.currentTimeMillis());

        return booking;
    }

    // ✅ Convert Entity to ResponseDTO
    public static SeatBookingResponseDTO toResponse(SeatBooking booking) {
        if (booking == null) return null;

        return SeatBookingResponseDTO.builder()
                .allocationId(booking.getAllocationId())
                .seatNumber(booking.getSeat() != null ? booking.getSeat().getSeatNumber() : null)
                .employeeName(booking.getEmployee() != null
                        ? booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName()
                        : null)
                .allocationDate(booking.getAllocationDate())
                .status(booking.getStatus() != null ? booking.getStatus().name() : null)
                .isActive(booking.isActive())
                .build();
    }

    // ✅ Update existing booking safely
    public static void updateEntity(SeatBooking existing, SeatBookingRequestDTO dto, Seat seat, Employee employee) {
        existing.setSeat(seat);
        existing.setEmployee(employee);
        existing.setStatus(SeatBooking.BookingStatus.valueOf(dto.getStatus()));

        // ✅ Only update date if explicitly passed
        if (dto.getAllocationDate() != null) {
            existing.setAllocationDate(dto.getAllocationDate());
        }
    }
}
