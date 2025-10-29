package com.ait.mrb_fp.service.impl;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.*;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.SeatBookingMapper;
import com.ait.mrb_fp.repository.*;
import com.ait.mrb_fp.service.SeatBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatBookingServiceImpl implements SeatBookingService {

    private final SeatBookingRepository seatBookingRepo;
    private final SeatRepository seatRepo;
    private final EmployeeRepository employeeRepo;

    public SeatBookingServiceImpl(SeatBookingRepository seatBookingRepo, SeatRepository seatRepo, EmployeeRepository employeeRepo) {
        this.seatBookingRepo = seatBookingRepo;
        this.seatRepo = seatRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
        Seat seat = seatRepo.findById(dto.getSeatId()).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
        seatBookingRepo.save(booking);
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public SeatBookingResponseDTO getById(String id) {
        SeatBooking booking = seatBookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> getAll() {
        return seatBookingRepo.findAll().stream().map(SeatBookingMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto) {
        SeatBooking existing = seatBookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        Seat seat = seatRepo.findById(dto.getSeatId()).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        SeatBookingMapper.updateEntity(existing, dto, seat, employee);
        seatBookingRepo.save(existing);
        return SeatBookingMapper.toResponse(existing);
    }

    @Override
    public void delete(String id) {
        seatBookingRepo.deleteById(id);
    }
}
