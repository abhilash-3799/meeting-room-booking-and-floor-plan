package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.SeatRequestDTO;
import com.ait.mrbfp.dto.response.SeatResponseDTO;

import java.util.List;

public interface SeatService {

    SeatResponseDTO createSeat(SeatRequestDTO dto);
    List<SeatResponseDTO> getAllSeats();
    SeatResponseDTO getSeatById(String seatId);
    List<SeatResponseDTO> getSeatsByOffice(String officeId);
    SeatResponseDTO updateSeat(String seatId, SeatRequestDTO dto);
    void deleteSeat(String seatId);
}
