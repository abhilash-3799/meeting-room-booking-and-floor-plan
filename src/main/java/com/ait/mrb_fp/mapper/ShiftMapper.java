package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.ShiftDTO;
import com.ait.mrb_fp.entity.Shift;

public class ShiftMapper {

    public static ShiftDTO toDTO(Shift shift) {
        if (shift == null) return null;
        return ShiftDTO.builder()
                .shiftId(shift.getShiftId())
                .shiftName(shift.getShiftName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .description(shift.getDescription())
                .isActive(shift.isActive())
                .build();
    }

    public static Shift toEntity(ShiftDTO dto) {
        if (dto == null) return null;
        return Shift.builder()
                .shiftId(dto.getShiftId())
                .shiftName(dto.getShiftName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .description(dto.getDescription())
                .isActive(dto.isActive())
                .build();
    }
}
