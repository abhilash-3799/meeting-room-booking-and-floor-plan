package com.ait.mrbfp.service.impl;

import com.ait.mrbfp.dto.request.SeatBookingRequestDTO;
import com.ait.mrbfp.dto.response.SeatBookingResponseDTO;
import com.ait.mrbfp.entity.*;
import com.ait.mrbfp.mapper.SeatBookingMapper;
import com.ait.mrbfp.repository.*;
import com.ait.mrbfp.service.SeatBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeatBookingServiceImpl implements SeatBookingService {

    private final SeatBookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public SeatBookingResponseDTO createBooking(SeatBookingRequestDTO dto) {
        log.info("Creating seat booking for seat {} and employee {}", dto.getSeatId(), dto.getEmployeeId());

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found: " + dto.getSeatId()));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + dto.getEmployeeId()));

        SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
        bookingRepository.save(booking);

        if (booking.getStatus() == SeatBooking.BookingStatus.ALLOCATED) {
            seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
            seat.setAvailable(false);
            seatRepository.save(seat);
        }

        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(SeatBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatBookingResponseDTO getBookingById(String allocationId) {
        SeatBooking booking = bookingRepository.findById(allocationId)
                .orElseThrow(() -> new RuntimeException("Seat booking not found: " + allocationId));
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> getBookingsByEmployee(String employeeId) {
        return bookingRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(SeatBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatBookingResponseDTO updateBookingStatus(String allocationId, String newStatus) {
        SeatBooking booking = bookingRepository.findById(allocationId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + allocationId));

        try {
            booking.setStatus(SeatBooking.BookingStatus.valueOf(newStatus.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid booking status: " + newStatus);
        }

        Seat seat = booking.getSeat();
        if (booking.getStatus() == SeatBooking.BookingStatus.RELEASED) {
            seat.setSeatStatus(Seat.SeatStatus.UNALLOCATED);
            seat.setAvailable(true);
            seatRepository.save(seat);
        }

        bookingRepository.save(booking);
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public void deleteBooking(String allocationId) {
        bookingRepository.deleteById(allocationId);
    }
}
