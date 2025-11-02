package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.ShiftRequestDTO;
import com.ait.mrbfp.dto.response.ShiftResponseDTO;
import java.util.List;

public interface ShiftService {

    ShiftResponseDTO createShift(ShiftRequestDTO requestDTO);

    List<ShiftResponseDTO> getAllShifts();

    ShiftResponseDTO getShiftById(String shiftId);

    ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO requestDTO);

    void deleteShift(String shiftId);
}
