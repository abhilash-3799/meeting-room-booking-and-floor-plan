package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;

public class OfficeMapper {

    public static Office toEntity(OfficeRequestDTO dto) {
        return Office.builder()
                .officeName(dto.getOfficeName())
                .location(dto.getLocation())
                .totalSeats(dto.getTotalSeats())
                .isActive(true)
                .build();
    }

    public static OfficeResponseDTO toResponse(Office entity) {
        return OfficeResponseDTO.builder()
                .officeId(entity.getOfficeId())
                .officeName(entity.getOfficeName())
                .location(entity.getLocation())
                .totalSeats(entity.getTotalSeats())
                .isActive(entity.isActive())
                .build();
    }
}
