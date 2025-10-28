package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.OfficeDTO;
import com.ait.mrb_fp.entity.Office;

public class OfficeMapper {

    public static OfficeDTO toDTO(Office office) {
        if (office == null) return null;
        return OfficeDTO.builder()
                .officeId(office.getOfficeId())
                .officeName(office.getOfficeName())
                .location(office.getLocation())
                .totalSeats(office.getTotalSeats())
                .isActive(office.isActive())
                .build();
    }

    public static Office toEntity(OfficeDTO dto) {
        if (dto == null) return null;
        return Office.builder()
                .officeId(dto.getOfficeId())
                .officeName(dto.getOfficeName())
                .location(dto.getLocation())
                .totalSeats(dto.getTotalSeats())
                .isActive(dto.isActive())
                .build();
    }
}
