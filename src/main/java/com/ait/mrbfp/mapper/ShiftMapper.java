package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.ShiftRequestDTO;
import com.ait.mrbfp.dto.response.ShiftResponseDTO;
import com.ait.mrbfp.entity.Shift;

public class ShiftMapper {

    private ShiftMapper() {}

    public static Shift toEntity(ShiftRequestDTO dto) {
        Shift shift = new Shift();
        shift.setShiftName(dto.getShiftName());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
        shift.setDescription(dto.getDescription());
        shift.setActive(dto.isActive());
        return shift;
    }

    public static ShiftResponseDTO toResponse(Shift shift) {
        ShiftResponseDTO dto = new ShiftResponseDTO();
        dto.setShiftId(shift.getShiftId());
        dto.setShiftName(shift.getShiftName());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setDescription(shift.getDescription());
        dto.setActive(shift.isActive());
        return dto;
    }

    public static void updateEntity(Shift existing, ShiftRequestDTO dto) {
        existing.setShiftName(dto.getShiftName());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setDescription(dto.getDescription());
        existing.setActive(dto.isActive());
    }
}
