
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

//    @Override
//    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
//        try {
//            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
//                throw new BadRequestException("Seat ID and Employee ID must not be null");
//            }
//
//            Seat seat = seatRepo.findById(dto.getSeatId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));
//
//            Employee employee = employeeRepo.findById(dto.getEmployeeId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
//
//
//
//            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), LocalDateTime.now());
//            if (isBooked) {
//                throw new BookingConflictException("Seat is already booked right now.");
//            }
//            boolean hasBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());
//            if (hasBooking)
//            {
//
//
//                boolean isTeamLead = employee.isTeamLead();
//
//                if (isTeamLead)
//                {
//
//
//                    boolean alreadyBookedThisSeat = seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatId(
//                            employee.getEmployeeId(), seat.getSeatId()
//                    );
//
//                    if (alreadyBookedThisSeat) {
//                        throw new BookingConflictException("Team Lead has already booked this seat.");
//                    }
//
//
//
//                    if (seat.getSeatStatus() == Seat.SeatStatus.ALLOCATED)
//                    {
//                        throw new InvalidStateException("Seat is already allocated to another employee.");
//                    }
//
//                    SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                    seatBookingRepo.save(booking);
//
//                    seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
//                    seatRepo.save(seat);
//                    return SeatBookingMapper.toResponse(booking);
//                }
//                else {
//                    throw new BookingConflictException("Employee can only book one seat at a time.");
//                }
//            }
//
//
//
//
//            else {
//
//                SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                seatBookingRepo.save(booking);
//                return SeatBookingMapper.toResponse(booking);
//            }
//
//        } catch (DataAccessException ex) {
//            throw new DatabaseException("Database operation failed: " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            throw new BadRequestException("Invalid request data: " + ex.getMessage());
//        } catch (Exception ex) {
//            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
//        }
//    }

//    @Override
//    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
//        try {
//            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
//                throw new BadRequestException("Seat ID and Employee ID must not be null");
//            }
//
//            Seat seat = seatRepo.findById(dto.getSeatId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));
//
//            Employee employee = employeeRepo.findById(dto.getEmployeeId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
//
//            // ✅ Get requested date (default: today)
//            LocalDateTime requestedDateTime = dto.getAllocationDate() != null ? dto.getAllocationDate() : LocalDateTime.now();
//
//
//            LocalDate requestedDate = requestedDateTime.toLocalDate();
//            // ✅ Check if same seat is already booked on the SAME DATE
//            boolean isSeatBookedSameDay = seatBookingRepo.existsBySeatAndDate(seat.getSeatId(), requestedDate);
//            if (isSeatBookedSameDay) {
//                throw new BookingConflictException("Seat is already booked for the selected date.");
//            }
//
//            boolean hasBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());
//            if (hasBooking) {
//
//                boolean isTeamLead = employee.isTeamLead();
//
//                if (isTeamLead) {
//
//                    boolean alreadyBookedThisSeatSameDate = seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
//                            employee.getEmployeeId(), seat.getSeatId(), requestedDateTime.toLocalDate()
//                    );
//
//                    // ✅ Allow booking same seat on different date
//                    if (alreadyBookedThisSeatSameDate) {
//                        throw new BookingConflictException("Team Lead has already booked this seat for the same date.");
//                    }
//
//                    // ✅ Check if seat is allocated for some *other employee* on same date
//                    boolean isSeatAllocatedForSameDate = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), requestedDateTime.toLocalDate());
//                    if (isSeatAllocatedForSameDate) {
//                        throw new InvalidStateException("Seat is already allocated to another employee for this date.");
//                    }
//
//                    // ✅ Proceed with booking (same seat allowed for diff date)
//                    SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                    booking.setAllocationDate(requestedDateTime);
//                    seatBookingRepo.save(booking);
//
//                    seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
//                    seatRepo.save(seat);
//
//                    return SeatBookingMapper.toResponse(booking);
//
//                } else {
//                    throw new BookingConflictException("Employee can only book one seat at a time.");
//                }
//
//            } else {
//                // ✅ Extra safety: check seat allocation for same date
//                boolean isSeatAllocatedForSameDate = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), requestedDate.toLocalDate());
//                if (isSeatAllocatedForSameDate) {
//                    throw new InvalidStateException("Seat is already allocated to another employee for this date.");
//                }
//
//                SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                booking.setAllocationDate(requestedDate);
//                seatBookingRepo.save(booking);
//
//                seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
//                seatRepo.save(seat);
//
//                return SeatBookingMapper.toResponse(booking);
//            }
//
//        } catch (InvalidStateException | BookingConflictException | ResourceNotFoundException | BadRequestException ex) {
//            throw ex;
//        } catch (DataAccessException ex) {
//            throw new DatabaseException("Database operation failed: " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            throw new BadRequestException("Invalid request data: " + ex.getMessage());
//        } catch (Exception ex) {
//            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
//        }
//    }

//    @Override
//    public SeatBookingResponseDTO create(SeatBookingRequestDTO dto) {
//        try {
//            if (dto.getSeatId() == null || dto.getEmployeeId() == null) {
//                throw new BadRequestException("Seat ID and Employee ID must not be null");
//            }
//
//            Seat seat = seatRepo.findById(dto.getSeatId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + dto.getSeatId()));
//
//            Employee employee = employeeRepo.findById(dto.getEmployeeId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
//
//            // ✅ Get requested date (default: today)
//            LocalDateTime requestedDateTime = dto.getAllocationDate() != null ? dto.getAllocationDate() : LocalDateTime.now();
//            LocalDate requestedDate = requestedDateTime.toLocalDate();
//
//            // ✅ Step 2: Check if same seat is already booked on the SAME DATE
//            boolean isSeatBookedSameDay = seatBookingRepo.existsBySeatAndDate(seat.getSeatId(), requestedDate);
//            if (isSeatBookedSameDay) {
//                throw new BookingConflictException("Seat is already booked for the selected date.");
//            }
//
//            boolean hasBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());
//            if (hasBooking) {
//
//                boolean isTeamLead = employee.isTeamLead();
//
//                if (isTeamLead) {
//
//                    // ✅ Check if same employee booked same seat on same date
//                    boolean alreadyBookedThisSeatSameDate =
//                            seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
//                                    employee.getEmployeeId(), seat.getSeatId(), requestedDate
//                            );
//
//                    if (alreadyBookedThisSeatSameDate) {
//                        throw new BookingConflictException("Team Lead has already booked this seat for the same date.");
//                    }
//
//                    // ✅ Check if seat is allocated to another employee for same date
//                    boolean isSeatAllocatedForSameDate = seatBookingRepo.existsBySeatAndDate(seat.getSeatId(), requestedDate);
//                    if (isSeatAllocatedForSameDate) {
//                        throw new InvalidStateException("Seat is already allocated to another employee for this date.");
//                    }
//
//                    // ✅ Allow booking same seat for different date
//                    SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                    booking.setAllocationDate(requestedDateTime);
//                    seatBookingRepo.save(booking);
//
//                    seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
//                    seatRepo.save(seat);
//
//                    return SeatBookingMapper.toResponse(booking);
//
//                } else {
//                    throw new BookingConflictException("Employee can only book one seat at a time.");
//                }
//
//            } else {
//                // ✅ Extra safety: check seat allocation for same date
//                boolean isSeatAllocatedForSameDate = seatBookingRepo.existsBySeatAndDate(seat.getSeatId(), requestedDate);
//                if (isSeatAllocatedForSameDate) {
//                    throw new InvalidStateException("Seat is already allocated to another employee for this date.");
//                }
//
//                SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
//                booking.setAllocationDate(requestedDateTime);
//                seatBookingRepo.save(booking);
//
//                seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
//                seatRepo.save(seat);
//
//                return SeatBookingMapper.toResponse(booking);
//            }
//
//        } catch (InvalidStateException | BookingConflictException | ResourceNotFoundException | BadRequestException ex) {
//            throw ex;
//        } catch (DataAccessException ex) {
//            throw new DatabaseException("Database operation failed: " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            throw new BadRequestException("Invalid request data: " + ex.getMessage());
//        } catch (Exception ex) {
//            throw new InternalServerException("Unexpected error occurred: " + ex.getMessage());
//        }
//    }


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

            // ✅ Get requested date (default: today)
            LocalDateTime requestedDateTime = dto.getAllocationDate() != null ? dto.getAllocationDate() : LocalDateTime.now();
            LocalDate requestedDate = requestedDateTime.toLocalDate();
            LocalDateTime startOfDay = requestedDate.atStartOfDay();
            LocalDateTime endOfDay = requestedDate.atTime(LocalTime.MAX);

            // ✅ Step 1: Check if same seat is already booked for this date (by another employee)
            boolean isSeatBookedByAnotherEmployeeSameDay =
                    seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay)
                            && seatBookingRepo.existsBySeat_SeatIdAndAllocationDateAndEmployee_EmployeeIdNot(
                            seat.getSeatId(),
                            requestedDateTime,
                            employee.getEmployeeId()
                    );

            if (isSeatBookedByAnotherEmployeeSameDay) {
                throw new BookingConflictException("Seat is already booked by another employee for the selected date.");
            }

            // ✅ Step 2: Check if same employee already booked same seat on the same date
            boolean isSameEmployeeBookedSameSeatSameDate =
                    seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
                            employee.getEmployeeId(),
                            seat.getSeatId(),
                            requestedDateTime
                    );

            if (isSameEmployeeBookedSameSeatSameDate) {
                throw new BookingConflictException("You have already booked this seat for the same date.");
            }

            // ✅ Step 3: (Your original logic starts here)
            boolean isBooked = seatBookingRepo.existsBySeat_SeatIdAndAllocationDate(seat.getSeatId(), LocalDateTime.now());
            if (isBooked) {
                throw new BookingConflictException("Seat is already booked right now.");
            }

            boolean hasBooking = seatBookingRepo.existsByEmployee_EmployeeId(employee.getEmployeeId());
            if (hasBooking) {

                boolean isTeamLead = employee.isTeamLead();

                if (isTeamLead) {

                    boolean alreadyBookedThisSeat = seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatId(
                            employee.getEmployeeId(), seat.getSeatId()
                    );

                    if (alreadyBookedThisSeat) {
                        // ✅ Allow same employee to book same seat on another date
                        boolean isAlreadyBookedThisSeatSameDay =
                                seatBookingRepo.existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
                                        employee.getEmployeeId(),
                                        seat.getSeatId(),
                                        requestedDateTime
                                );

                        if (isAlreadyBookedThisSeatSameDay) {
                            throw new BookingConflictException("Team Lead has already booked this seat for the same date.");
                        }
                    }

                    // ✅ Check if seat is allocated to another employee for same date
                    boolean isSeatAllocatedForSameDate =
                            seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay);
                    if (isSeatAllocatedForSameDate) {
                        throw new InvalidStateException("Seat is already allocated to another employee for this date.");
                    }

                    // ✅ Proceed with booking
                    SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
                    booking.setAllocationDate(requestedDateTime);
                    seatBookingRepo.save(booking);

                    seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
                    seatRepo.save(seat);

                    return SeatBookingMapper.toResponse(booking);

                } else {
                    throw new BookingConflictException("Employee can only book one seat at a time.");
                }

            } else {
                // ✅ Extra safety: check again for allocation before booking
                boolean isSeatAllocatedForSameDate =
                        seatBookingRepo.existsBySeatAndDateRange(seat.getSeatId(), startOfDay, endOfDay);
                if (isSeatAllocatedForSameDate) {
                    throw new InvalidStateException("Seat is already allocated to another employee for this date.");
                }

                SeatBooking booking = SeatBookingMapper.toEntity(dto, seat, employee);
                booking.setAllocationDate(requestedDateTime);
                seatBookingRepo.save(booking);

                seat.setSeatStatus(Seat.SeatStatus.ALLOCATED);
                seatRepo.save(seat);

                return SeatBookingMapper.toResponse(booking);
            }

        } catch (InvalidStateException | BookingConflictException | ResourceNotFoundException | BadRequestException ex) {
            throw ex;
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

        return dtos.stream().map(dto -> {
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
    }
}
