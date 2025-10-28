package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Queue;

public class SeatMapper {

    public static Seat toEntity(SeatRequestDTO dto, Office office, Team team, Queue queue) {
        return Seat.builder()
                .office(office)
                .seatNumber(dto.getSeatNumber())
                .assignedTeam(team)
                .queue(queue)
                .seatStatus(Seat.SeatStatus.valueOf(dto.getSeatStatus()))
                .isAvailable(dto.isAvailable())
                .isActive(true)
                .build();
    }

    public static SeatResponseDTO toResponse(Seat entity) {
        return SeatResponseDTO.builder()
                .seatId(entity.getSeatId())
                .seatNumber(entity.getSeatNumber())
                .officeName(entity.getOffice().getOfficeName())
                .teamName(entity.getAssignedTeam() != null ? entity.getAssignedTeam().getTeamName() : null)
                .queueName(entity.getQueue() != null ? entity.getQueue().getQueueName() : null)
                .seatStatus(entity.getSeatStatus().name())
                .isAvailable(entity.isAvailable())
                .isActive(entity.isActive())
                .build();
    }
}
