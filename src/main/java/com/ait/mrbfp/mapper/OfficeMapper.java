package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.OfficeRequestDTO;
import com.ait.mrbfp.dto.response.OfficeResponseDTO;
import com.ait.mrbfp.entity.Office;

public class OfficeMapper {

    private OfficeMapper() {
    }

    public static Office toEntity(OfficeRequestDTO dto) {
        Office o = new Office();
        o.setOfficeName(dto.getOfficeName());
        o.setLocation(dto.getLocation());
        o.setTotalSeats(dto.getTotalSeats());
        o.setActive(dto.isActive());
        return o;
    }

    public static OfficeResponseDTO toResponse(Office o) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setOfficeId(o.getOfficeId());
        dto.setOfficeName(o.getOfficeName());
        dto.setLocation(o.getLocation());
        dto.setTotalSeats(o.getTotalSeats());
        dto.setActive(o.isActive());
        return dto;
    }

    public static void updateEntity(Office existing, OfficeRequestDTO dto) {
        existing.setOfficeName(dto.getOfficeName());
        existing.setLocation(dto.getLocation());
        existing.setTotalSeats(dto.getTotalSeats());
        existing.setActive(dto.isActive());
    }
}
