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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("Creating seat booking for employee ID: {}", dto.getEmployeeId());
        try {
            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
                throw new BadRequestException("Seat ID and Employee ID must not be null");
            }

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            LocalDateTime requestedDateTime = dto.getAllocationDate() != null ? dto.getAllocationDate() : LocalDateTime.now();
            LocalDate requestedDate = requestedDateTime.toLocalDate();
            LocalDateTime startOfDay = requestedDate.atStartOfDay();
            LocalDateTime endOfDay = requestedDate.atTime(LocalTime.MAX);

            boolean isSeatBookedByAnotherEmployeeSameDay =
                    seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay)
                            && seatBookingRepo.existsBySeat_SeatIdAndAllocationDateAndEmployee_EmployeeIdNot(
                            seat.getSeatId(), requestedDateTime, employee.getEmployeeId());

            if (isSeatBookedByAnotherEmployeeSameDay) {
                throw new BookingConflictException("Seat is already booked by another employee for the selected date.");
            }

            boolean isSameEmployeeBookedSameSeatSameDate =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
                            employee.getEmployeeId(), seat.getSeatId(), requestedDateTime);

            if (isSameEmployeeBookedSameSeatSameDate) {
                throw new BookingConflictException("You have already booked this seat for the same date.");
            }

            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), LocalDateTime.now());
            if (isBooked) {
                throw new BookingConflictException("Seat is already booked right now.");
            }

            boolean hasBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());
            if (hasBooking && !employee.isTeamLead()) {
                throw new BookingConflictException("Employee can only book one seat at a time.");
            }

            SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
            booking.setAllocationDate(requestedDateTime);
            seatBookingRepo.save(booking);

            seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
            seatRepo.save(seat);

            log.info("Seat booking created successfully for employee ID: {}", employee.getEmployeeId());
            return SeatBookingMapper.toResponse(booking);

        } catch (InvalidStateException | BookingConflictException | ResourceNotFoundException | BadRequestException ex) {
            log.error("Seat booking failed: {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while booking seat: {}", ex.getMessage());
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while booking seat: {}", ex.getMessage());
            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public SeatBookingResponseDTO getById(String id) {
        log.info("Fetching seat booking with ID: {}", id);
        try {
            SeatBooking booking = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
            return SeatBookingMapper.toResponse(booking);
        } catch (ResourceNotFoundException ex) {
            log.error("Booking not found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching booking ID {}: {}", id, ex.getMessage());
            throw new InternalServerException("Unexpected error fetching booking: " + ex.getMessage());
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
        } catch (DataAccessException ex) {
            log.error("Database error fetching bookings: {}", ex.getMessage());
            throw new DatabaseException("Failed to fetch seat bookings from database.");
        }
    }

    @Override
    public SeatBookingResponseDTO update(String id, SeatBookingRequestDTO dto) {
        log.info("Updating booking ID: {}", id);
        try {
            SeatBooking existing = seatBookingRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

            Seat seat = seatRepo.findById(dto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));

            Employee employee = employeeRepo.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

            SeatBookingMapper.updateEntity(existing, dto, seat, employee);
            seatBookingRepo.save(existing);

            log.info("Booking updated successfully with ID: {}", id);
            return SeatBookingMapper.toResponse(existing);
        } catch (DataAccessException ex) {
            log.error("Database error while updating booking {}: {}", id, ex.getMessage());
            throw new DatabaseException("Database operation failed: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error updating booking {}: {}", id, ex.getMessage());
            throw new InternalServerException("Unexpected error occurred while updating: " + ex.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting booking with ID: {}", id);
        try {
            if (!seatBookingRepo.existsById(id)) {
                throw new ResourceNotFoundException("Booking not found with ID: " + id);
            }
            seatBookingRepo.deleteById(id);
            log.info("Booking deleted successfully with ID: {}", id);
        } catch (DataAccessException ex) {
            log.error("Database error while deleting booking {}: {}", id, ex.getMessage());
            throw new ForeignKeyConstraintException("Failed to delete booking due to foreign key constraint.");
        } catch (Exception ex) {
            log.error("Unexpected error while deleting booking {}: {}", id, ex.getMessage());
            throw new InternalServerException("Unexpected error while deleting booking: " + ex.getMessage());
        }
    }

    @Override
    public List<SeatBookingResponseDTO> createBulk(List<SeatBookingRequestDTO> dtos, String teamLeadId) {
        log.info("Bulk booking initiated by team lead ID: {}", teamLeadId);
        try {
            Employee teamLead = employeeRepo.findById(teamLeadId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team Lead not found with ID: " + teamLeadId));

            if (!teamLead.isTeamLead()) {
                throw new UnauthorizedException("Only Team Leads can perform bulk bookings.");
            }

            List<SeatBookingResponseDTO> responses = dtos.stream().map(dto -> {
                Seat seat = seatRepo.findById(dto.getSeatId())
                        .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));
                Employee employee = employeeRepo.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

                boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), LocalDateTime.now());
                if (isBooked) {
                    throw new BookingConflictException("Seat already booked: " + seat.getSeatNumber());
                }

                seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
                seat.setAvailable(false);
                seatRepo.save(seat);

                SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
                seatBookingRepo.save(booking);

                return SeatBookingMapper.toResponse(booking);
            }).collect(Collectors.toList());

            log.info("Bulk booking completed successfully by team lead ID: {}", teamLeadId);
            return responses;
        } catch (Exception ex) {
            log.error("Bulk booking failed: {}", ex.getMessage());
            throw ex;
        }
    }
}
