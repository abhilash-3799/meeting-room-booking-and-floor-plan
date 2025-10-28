package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.SeatDTO;
import com.ait.mrb_fp.entity.*;

public class SeatMapper {

    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) return null;

        return SeatDTO.builder()
                .seatId(seat.getSeatId())
                .officeId(seat.getOffice() != null ? seat.getOffice().getOfficeId() : null)
                .officeName(seat.getOffice() != null ? seat.getOffice().getOfficeName() : null)
                .seatNumber(seat.getSeatNumber())
                .seatStatus(seat.getSeatStatus().name())
                .assignedTeamId(seat.getAssignedTeam() != null ? seat.getAssignedTeam().getTeamId() : null)
                .assignedTeamName(seat.getAssignedTeam() != null ? seat.getAssignedTeam().getTeamName() : null)
                .queueId(seat.getQueue() != null ? seat.getQueue().getQueueId() : null)
                .queueName(seat.getQueue() != null ? seat.getQueue().getQueueName() : null)
                .isAvailable(seat.isAvailable())
                .isActive(seat.isActive())
                .build();
    }

    public static Seat toEntity(SeatDTO dto) {
        if (dto == null) return null;

        Office office = dto.getOfficeId() != null ? Office.builder().officeId(dto.getOfficeId()).build() : null;
        Team team = dto.getAssignedTeamId() != null ? Team.builder().teamId(dto.getAssignedTeamId()).build() : null;
        Queue queue = dto.getQueueId() != null ? Queue.builder().queueId(dto.getQueueId()).build() : null;

        return Seat.builder()
                .seatId(dto.getSeatId())
                .office(office)
                .seatNumber(dto.getSeatNumber())
                .seatStatus(Seat.SeatStatus.valueOf(dto.getSeatStatus()))
                .assignedTeam(team)
                .queue(queue)
                .isAvailable(dto.isAvailable())
                .isActive(dto.isActive())
                .build();
    }
}
