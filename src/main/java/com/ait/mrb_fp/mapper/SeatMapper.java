package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.SeatDTO;
import com.ait.mrb_fp.entity.Seat;

public class SeatMapper {

    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) return null;
        return SeatDTO.builder()
                .seatId(seat.getSeatId())
                .officeId(seat.getOfficeId())
                .seatNumber(seat.getSeatNumber())
                .seatStatus(seat.getSeatStatus().name())
                .assignedTeamId(seat.getAssignedTeamId())
                .queueId(seat.getQueueId())
                .isAvailable(seat.isAvailable())
                .isActive(seat.isActive())
                .build();
    }

    public static Seat toEntity(SeatDTO dto) {
        if (dto == null) return null;
        return Seat.builder()
                .seatId(dto.getSeatId())
                .officeId(dto.getOfficeId())
                .seatNumber(dto.getSeatNumber())
                .seatStatus(Seat.SeatStatus.valueOf(dto.getSeatStatus()))
                .assignedTeamId(dto.getAssignedTeamId())
                .queueId(dto.getQueueId())
                .isAvailable(dto.isAvailable())
                .isActive(dto.isActive())
                .build();
    }
}
