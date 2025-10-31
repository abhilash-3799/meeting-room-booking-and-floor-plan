package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrb_fp.entity.MeetingRoomBooking;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.mapper.MeetingRoomBookingMapper;
import com.ait.mrb_fp.repository.MeetingRoomBookingRepository;
import com.ait.mrb_fp.repository.MeetingRoomRepository;
import com.ait.mrb_fp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
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
            return bookingRepository.findAll()
                    .stream()
                    .map(MeetingRoomBookingMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching meeting room bookings: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public MeetingRoomBookingResponseDTO getById(String id) {
        log.info("Fetching meeting room booking with ID: {}", id);
        try {
            MeetingRoomBooking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meeting booking not found: " + id));
            return MeetingRoomBookingMapper.toResponse(booking);
        } catch (Exception e) {
            log.error("Error fetching meeting room booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public MeetingRoomBookingResponseDTO create(MeetingRoomBookingRequestDTO request) {
        log.info("Creating new meeting room booking for room ID: {}", request.getRoomId());
        try {
            MeetingRoom room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Meeting room not found: " + request.getRoomId()));

            Employee bookedBy = employeeRepository.findById(request.getBookedByEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getBookedByEmployeeId()));

            MeetingRoomBooking booking = MeetingRoomBookingMapper.toEntity(request, room, bookedBy);
            bookingRepository.save(booking);
            log.info("Meeting room booking created successfully with ID: {}", booking.getMeetingId());
            return MeetingRoomBookingMapper.toResponse(booking);
        } catch (Exception e) {
            log.error("Error creating meeting room booking: {}", e.getMessage());
            throw e;
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
            log.info("Meeting room booking with ID {} updated successfully", id);
            return MeetingRoomBookingMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating meeting room booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting meeting room booking with ID: {}", id);
        try {
            bookingRepository.deleteById(id);
            log.info("Meeting room booking with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting meeting room booking ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
