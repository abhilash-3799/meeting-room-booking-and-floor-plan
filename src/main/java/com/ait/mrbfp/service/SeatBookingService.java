package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.SeatBookingRequestDTO;
import com.ait.mrbfp.dto.response.SeatBookingResponseDTO;

import java.util.List;

public interface SeatBookingService {

    SeatBookingResponseDTO createBooking(SeatBookingRequestDTO dto);

    List<SeatBookingResponseDTO> getAllBookings();

    SeatBookingResponseDTO getBookingById(String allocationId);

    List<SeatBookingResponseDTO> getBookingsByEmployee(String employeeId);

    SeatBookingResponseDTO updateBookingStatus(String allocationId, String newStatus);

    void deleteBooking(String allocationId);
}
