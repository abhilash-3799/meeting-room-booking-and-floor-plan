package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.SeatRequestDTO;
import com.ait.mrbfp.dto.response.SeatResponseDTO;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.entity.Queue;
import com.ait.mrbfp.entity.Seat;
import com.ait.mrbfp.entity.Team;
import com.ait.mrbfp.exception.BadRequestException;

public class SeatMapper {

    private SeatMapper() {
    }

    public static Seat toEntity(SeatRequestDTO dto, Office office, Team team, Queue queue) {
        Seat seat = new Seat();
        seat.setOffice(office);
        seat.setAssignedTeam(team);
        seat.setQueue(queue);
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setAvailable(dto.getIsAvailable());
        seat.setActive(true);

        try {
            seat.setSeatStatus(Seat.SeatStatus.valueOf(dto.getSeatStatus().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid seat status: " + dto.getSeatStatus());
        }

        return seat;
    }

    public static SeatResponseDTO toResponse(Seat seat) {
        SeatResponseDTO dto = new SeatResponseDTO();
        dto.setSeatId(seat.getSeatId());
        dto.setOfficeName(seat.getOffice() != null ? seat.getOffice().getOfficeName() : null);
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatStatus(seat.getSeatStatus().name());
        dto.setTeamName(seat.getAssignedTeam() != null ? seat.getAssignedTeam().getTeamName() : null);
        dto.setQueueName(seat.getQueue() != null ? seat.getQueue().getQueueName() : null);
        dto.setAvailable(seat.isAvailable());
        dto.setActive(seat.isActive());
        return dto;
    }

    public static void updateEntity(Seat existing, SeatRequestDTO dto, Office office, Team team, Queue queue) {
        existing.setOffice(office);
        existing.setAssignedTeam(team);
        existing.setQueue(queue);
        existing.setSeatNumber(dto.getSeatNumber());
        existing.setAvailable(dto.getIsAvailable());

        try {
            existing.setSeatStatus(Seat.SeatStatus.valueOf(dto.getSeatStatus().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid seat status: " + dto.getSeatStatus());
        }
    }
}
