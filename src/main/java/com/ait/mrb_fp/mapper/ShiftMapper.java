package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;

public class ShiftMapper {

    private ShiftMapper() {}

    public static Shift toEntity(ShiftRequestDTO r) {
        Shift s = new Shift();
        s.setShiftName(r.getShiftName());
        s.setStartTime(r.getStartTime());
        s.setEndTime(r.getEndTime());
        s.setDescription(r.getDescription());
        s.setActive(true);
        return s;
    }

    public static ShiftResponseDTO toResponse(Shift s) {
        ShiftResponseDTO r = new ShiftResponseDTO();
        r.setShiftId(s.getShiftId());
        r.setShiftName(s.getShiftName());
        r.setStartTime(s.getStartTime());
        r.setEndTime(s.getEndTime());
        r.setDescription(s.getDescription());
        r.setActive(s.isActive());
        return r;
    }

    public static void updateEntity(Shift s, ShiftRequestDTO r) {
        s.setShiftName(r.getShiftName());
        s.setStartTime(r.getStartTime());
        s.setEndTime(r.getEndTime());
        s.setDescription(r.getDescription());
    }
}
