package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.MeetingRoom;
import com.ait.mrbfp.entity.MeetingRoomBooking;
import com.ait.mrbfp.mapper.MeetingRoomBookingMapper;
import com.ait.mrbfp.repository.EmployeeRepository;
import com.ait.mrbfp.repository.MeetingRoomBookingRepository;
import com.ait.mrbfp.repository.MeetingRoomRepository;
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
public class MeetingRoomBookingServiceImpl implements MeetingRoomBookingService {

    private final MeetingRoomBookingRepository bookingRepository;
    private final MeetingRoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public MeetingRoomBookingResponseDTO createBooking(MeetingRoomBookingRequestDTO dto) {
        log.info("Creating meeting booking for room ID: {}", dto.getRoomId());

        MeetingRoom room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Meeting room not found"));
        Employee employee = employeeRepository.findById(dto.getBookedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        MeetingRoomBooking booking = MeetingRoomBookingMapper.toEntity(dto, room, employee);
        bookingRepository.save(booking);
        log.info("Meeting booking created successfully with ID: {}", booking.getMeetingId());
        return MeetingRoomBookingMapper.toResponse(booking);
    }

    @Override
    public List<MeetingRoomBookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(MeetingRoomBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingRoomBookingResponseDTO getBookingById(String bookingId) {
        MeetingRoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
        return MeetingRoomBookingMapper.toResponse(booking);
    }

    @Override
    public MeetingRoomBookingResponseDTO updateBooking(String bookingId, MeetingRoomBookingRequestDTO dto) {
        MeetingRoomBooking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        MeetingRoom room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Meeting room not found"));
        Employee employee = employeeRepository.findById(dto.getBookedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        MeetingRoomBookingMapper.updateEntity(existing, dto, room, employee);
        bookingRepository.save(existing);
        return MeetingRoomBookingMapper.toResponse(existing);
    }

    @Override
    public void cancelBooking(String bookingId) {
        MeetingRoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(MeetingRoomBooking.MeetingStatus.CANCELLED);
        bookingRepository.save(booking);
        log.warn("Booking cancelled with ID: {}", bookingId);
    }
}
