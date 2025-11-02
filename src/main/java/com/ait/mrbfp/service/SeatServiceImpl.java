package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.SeatRequestDTO;
import com.ait.mrbfp.dto.response.SeatResponseDTO;
import com.ait.mrbfp.entity.*;
import com.ait.mrbfp.mapper.SeatMapper;
import com.ait.mrbfp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final OfficeRepository officeRepository;
    private final TeamRepository teamRepository;
    private final QueueRepository queueRepository;

    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO dto) {
        log.info("Creating seat: {}", dto.getSeatNumber());

        if (seatRepository.existsBySeatNumber(dto.getSeatNumber())) {
            log.error("Seat number already exists: {}", dto.getSeatNumber());
            throw new RuntimeException("Seat number already exists: " + dto.getSeatNumber());
        }

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found: " + dto.getOfficeId()));

        Team team = dto.getAssignedTeamId() != null
                ? teamRepository.findById(dto.getAssignedTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found: " + dto.getAssignedTeamId()))
                : null;

        Queue queue = dto.getQueueId() != null
                ? queueRepository.findById(dto.getQueueId())
                .orElseThrow(() -> new RuntimeException("Queue not found: " + dto.getQueueId()))
                : null;

        Seat seat = SeatMapper.toEntity(dto, office, team, queue);
        seatRepository.save(seat);

        log.info("Seat created successfully with ID: {}", seat.getSeatId());
        return SeatMapper.toResponse(seat);
    }

    @Override
    public List<SeatResponseDTO> getAllSeats() {
        log.info("Fetching all seats...");
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponseDTO getSeatById(String seatId) {
        log.info("Fetching seat with ID: {}", seatId);
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));
        return SeatMapper.toResponse(seat);
    }

    @Override
    public List<SeatResponseDTO> getSeatsByOffice(String officeId) {
        log.info("Fetching seats for office ID: {}", officeId);
        return seatRepository.findByOffice_OfficeId(officeId)
                .stream()
                .map(SeatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponseDTO updateSeat(String seatId, SeatRequestDTO dto) {
        log.info("Updating seat with ID: {}", seatId);
        Seat existing = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found: " + dto.getOfficeId()));

        Team team = dto.getAssignedTeamId() != null
                ? teamRepository.findById(dto.getAssignedTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found: " + dto.getAssignedTeamId()))
                : null;

        Queue queue = dto.getQueueId() != null
                ? queueRepository.findById(dto.getQueueId())
                .orElseThrow(() -> new RuntimeException("Queue not found: " + dto.getQueueId()))
                : null;

        SeatMapper.updateEntity(existing, dto, office, team, queue);
        seatRepository.save(existing);

        log.info("Seat updated successfully: {}", seatId);
        return SeatMapper.toResponse(existing);
    }

    @Override
    public void deleteSeat(String seatId) {
        log.warn("Deleting seat with ID: {}", seatId);
        seatRepository.deleteById(seatId);
    }
}
