package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.entity.MeetingRoomBooking;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.exception.BadRequestException;
import com.ait.mrb_fp.mapper.MeetingRoomBookingMapper;
import com.ait.mrb_fp.repository.MeetingRoomBookingRepository;
import com.ait.mrb_fp.repository.MeetingRoomRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeetingRoomBookingServiceImpl implements MeetingRoomBookingService {

    private final MeetingRoomBookingRepository bookingRepository;
    private final MeetingRoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    public MeetingRoomBookingServiceImpl(
            MeetingRoomBookingRepository bookingRepository,
            MeetingRoomRepository roomRepository,
            EmployeeRepository employeeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<MeetingRoomBookingResponseDTO> getAll() {
        log.info("Fetching all meeting room bookings");
        try {
            List<MeetingRoomBookingResponseDTO> list = bookingRepository.findAll()
                    .stream()
                    .map(MeetingRoomBookingMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Total bookings fetched: {}", list.size());
            return list;
        } catch (Exception ex) {
            log.error("Error fetching meeting room bookings: {}", ex.getMessage());
            throw new RuntimeException("Error fetching meeting room bookings.");
        }
    }

    @Override
    public MeetingRoomBookingResponseDTO getById(String id) {
        log.info("Fetching meeting room booking by ID: {}", id);
        try {
            MeetingRoomBooking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting booking not found: " + id));
            log.info("Meeting booking found: {}", id);
            return MeetingRoomBookingMapper.toResponse(booking);
        } catch (Exception ex) {
            log.error("Error fetching booking by ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error fetching booking with ID: " + id);
        }
    }

    @Override
    public MeetingRoomBookingResponseDTO create(MeetingRoomBookingRequestDTO request) {
        log.info("Creating meeting room booking for employee ID: {}", request.getBookedByEmployeeId());
        try {
            MeetingRoom room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Meeting room not found: " + request.getRoomId()));

            Employee bookedBy = employeeRepository.findById(request.getBookedByEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getBookedByEmployeeId()));

            if (!bookedBy.isTeamLead()) {
                throw new RuntimeException("Only Team Lead employees are allowed to book meeting rooms.");
            }

            boolean overlappingBookingExists = bookingRepository
                    .existsByRoom_RoomIdAndMeetingDateAndStartTimeLessThanAndEndTimeGreaterThan(
                            room.getRoomId(),
                            request.getMeetingDate(),
                            request.getEndTime(),
                            request.getStartTime()
                    );

            if (overlappingBookingExists) {
                throw new RuntimeException("Meeting room is already booked for the selected date and time range.");
            }

            boolean alreadyBookedThisRoom = bookingRepository.existsByBookedBy_EmployeeIdAndRoom_RoomId(
                    bookedBy.getEmployeeId(), room.getRoomId()
            );

            if (alreadyBookedThisRoom && overlappingBookingExists) {
                throw new RuntimeException("Team Lead has already booked this meeting room for the same time slot.");
            }

            MeetingRoomBooking booking = MeetingRoomBookingMapper.toEntity(request, room, bookedBy);
            booking.setMeetingId("MR" + System.currentTimeMillis());
            booking.setStatus(MeetingRoomBooking.MeetingStatus.BOOKED);
            booking.setActive(true);

            room.setRoomStatus(MeetingRoom.MeetingRoomStatus.NOT_AVAILABLE);
            roomRepository.save(room);
            bookingRepository.save(booking);

            log.info("Meeting room booking created successfully with ID: {}", booking.getMeetingId());
            return MeetingRoomBookingMapper.toResponse(booking);

        } catch (Exception ex) {
            log.error("Error creating meeting room booking: {}", ex.getMessage());
            throw new RuntimeException("Error creating meeting room booking.");
        }
    }

    @Override
    public MeetingRoomBookingResponseDTO update(String id, MeetingRoomBookingRequestDTO request) {
        log.info("Updating meeting room booking with ID: {}", id);
        try {
            MeetingRoomBooking existing = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting booking not found: " + id));

            MeetingRoom room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Meeting room not found: " + request.getRoomId()));

            Employee bookedBy = employeeRepository.findById(request.getBookedByEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getBookedByEmployeeId()));

            MeetingRoomBookingMapper.updateEntity(existing, request, room, bookedBy);
            bookingRepository.save(existing);

            log.info("Meeting room booking updated successfully: {}", id);
            return MeetingRoomBookingMapper.toResponse(existing);
        } catch (Exception ex) {
            log.error("Error updating booking with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error updating booking with ID: " + id);
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting meeting room booking with ID: {}", id);
        try {
            bookingRepository.deleteById(id);
            log.info("Meeting room booking deleted successfully: {}", id);
        } catch (Exception ex) {
            log.error("Error deleting booking with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error deleting booking with ID: " + id);
        }
    }
}
