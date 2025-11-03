package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;

import java.util.List;

public interface ShiftService {
    ShiftResponseDTO createShift(ShiftRequestDTO dto);
    ShiftResponseDTO getShiftById(String shiftId);
    List<ShiftResponseDTO> getAllShifts();
    ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO dto);
    void deactivateShift(String shiftId);
}
