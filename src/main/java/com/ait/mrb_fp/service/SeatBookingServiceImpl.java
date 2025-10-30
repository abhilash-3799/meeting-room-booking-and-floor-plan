package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.SeatBooking;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.SeatBookingMapper;
import com.ait.mrb_fp.repository.SeatBookingRepository;
import com.ait.mrb_fp.repository.SeatRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Creating seat booking for employee ID: {}", dto.getEmployeeId());
        try {
            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
            seatBookingRepo.save(booking);
            log.info("Seat booking created successfully with ID: {}", booking.getAllocationId());
            return SeatBookingMapper.toResponse(booking);
        } catch (Exception e) {
            log.error("Error creating seat booking: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SeatBookingResponseDTO getById(String id) {
        log.info("Fetching seat booking with ID: {}", id);
        try {
            SeatBooking booking = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat booking not found"));
            return SeatBookingMapper.toResponse(booking);
        } catch (Exception e) {
            log.error("Error fetching seat booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SeatBookingResponseDTO> getAll() {
        log.info("Fetching all seat bookings");
        try {
            return seatBookingRepo.findAll()
                    .stream()
                    .map(SeatBookingMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching seat bookings: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto) {
        log.info("Updating seat booking with ID: {}", id);
        try {
            SeatBooking existing = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat booking not found"));

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            SeatBookingMapper.updateEntity(existing, dto, seat, employee);
            seatBookingRepo.save(existing);
            log.info("Seat booking with ID {} updated successfully", id);
            return SeatBookingMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating seat booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting seat booking with ID: {}", id);
        try {
            seatBookingRepo.deleteById(id);
            log.info("Seat booking with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting seat booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
