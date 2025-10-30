package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatBookingRequestDTO;
import com.ait.mrb_fp.dto.response.SeatBookingResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.SeatBooking;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.SeatBookingMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.SeatBookingRepository;
import com.ait.mrb_fp.repository.SeatRepository;
import com.ait.mrb_fp.service.SeatBookingService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatBookingServiceImpl implements SeatBookingService {

    private final SeatBookingRepository seatBookingRepo;
    private final SeatRepository seatRepo;
    private final EmployeeRepository employeeRepo;

    public SeatBookingServiceImpl(SeatBookingRepository seatBookingRepo,
                                  SeatRepository seatRepo,
                                  EmployeeRepository employeeRepo) {
        this.seatBookingRepo = seatBookingRepo;
        this.seatRepo = seatRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
        try {
            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
                throw new BadRequestException("Seat ID and Employee ID must not be null");
            }

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            // 🔍 Check for existing booking conflict
            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), dto.getAllocationDate());
            if (isBooked) {
                throw new BookingConflictException("Seat is already booked for this time: " + dto.getAllocationDate());
            }

            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);

            booking.setAllocationId("BK" + System.currentTimeMillis());
            booking.setAllocationDate(LocalDate.now());
            seatBookingRepo.save(booking);

            return SeatBookingMapper.toResponse(booking);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid request data: " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public SeatBookingResponseDTO getById(String id) {
        SeatBooking booking = seatBookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        return SeatBookingMapper.toResponse(booking);
    }

    @Override
    public List<SeatBookingResponseDTO> getAll() {
        try {
            return seatBookingRepo.findAll()
                    .stream()
                    .map(SeatBookingMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to fetch seat bookings from database.");
        }
    }

    @Override
    public SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto) {
        try {
            SeatBooking existing = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            // Prevent conflicting update
            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), dto.getAllocationDate());
            if (isBooked && !existing.getSeat().getSeatId().equals(dto.getSeatId())) {
                throw new BookingConflictException("Seat is already booked for this time: " + dto.getAllocationDate());
            }

            SeatBookingMapper.updateEntity(existing, dto, seat, employee);
            seatBookingRepo.save(existing);

            return SeatBookingMapper.toResponse(existing);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error occurred while updating: " + ex.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (!seatBookingRepo.existsById(id)) {
                throw new ResourceNotFoundException("Booking not found with ID: " + id);
            }
            seatBookingRepo.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ForeignKeyConstraintException("Failed to delete booking due to foreign key constraint.");
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error while deleting booking: " + ex.getMessage());
        }
    }

    @Override
    public List<SeatBookingResponseDTO> createBulk(List<SeatBookingRequestDTO> dtos, String teamLeadId) {
        Employee teamLead = employeeRepo.findById(teamLeadId)
                .orElseThrow(() -> new ResourceNotFoundException("Team Lead not found with ID: " + teamLeadId));



        if (!teamLead.isTeamLead()) {
            throw new UnauthorizedException("Only Team Leads can perform bulk bookings.");

        }



        List<SeatBookingResponseDTO> responses = dtos.stream().map(dto -> {
            // 🔁 Reuse single-seat booking logic
            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));
            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), dto.getAllocationDate());
            if (isBooked) {
                throw new BookingConflictException("Seat already booked: " + seat.getSeatNumber());
            }

            // 🔄 Allocate seat
            seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
            seat.setAvailable(false);
            seatRepo.save(seat);

            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
            booking.setAllocationDate(LocalDate.now());

            seatBookingRepo.save(booking);

            return SeatBookingMapper.toResponse(booking);
        }).collect(Collectors.toList());

        return responses;
    }

}
