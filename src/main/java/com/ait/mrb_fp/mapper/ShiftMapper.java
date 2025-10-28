package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;
import java.time.LocalTime;

public class ShiftMapper {

    public static Shift toEntity(ShiftRequestDTO dto) {
        return Shift.builder()
                .shiftName(dto.getShiftName())
                .startTime(LocalTime.parse(dto.getStartTime()))
                .endTime(LocalTime.parse(dto.getEndTime()))
                .description(dto.getDescription())
                .isActive(true)
                .build();
    }

    public static ShiftResponseDTO toResponse(Shift entity) {
        return ShiftResponseDTO.builder()
                .shiftId(entity.getShiftId())
                .shiftName(entity.getShiftName())
                .startTime(entity.getStartTime().toString())
                .endTime(entity.getEndTime().toString())
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .build();
    }
}
