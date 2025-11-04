package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomBookingResponseDTO;

import java.util.List;

public interface MeetingRoomBookingService {

    MeetingRoomBookingResponseDTO createBooking(MeetingRoomBookingRequestDTO dto);

    List<MeetingRoomBookingResponseDTO> getAllBookings();

    MeetingRoomBookingResponseDTO getBookingById(String bookingId);

    MeetingRoomBookingResponseDTO updateBooking(String bookingId, MeetingRoomBookingRequestDTO dto);

    void cancelBooking(String bookingId);
}
