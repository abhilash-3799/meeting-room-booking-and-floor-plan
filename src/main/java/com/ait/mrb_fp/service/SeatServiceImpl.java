package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.entity.*;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.SeatMapper;
import com.ait.mrb_fp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepo;
    private final OfficeRepository officeRepo;
    private final TeamRepository teamRepo;
    private final QueueRepository queueRepo;

    public SeatServiceImpl(SeatRepository seatRepo, OfficeRepository officeRepo,
                           TeamRepository teamRepo, QueueRepository queueRepo) {
        this.seatRepo = seatRepo;
        this.officeRepo = officeRepo;
        this.teamRepo = teamRepo;
        this.queueRepo = queueRepo;
    }

    @Override
    public SeatResponseDTO create(SeatRequestDTO dto) {
        log.info("Creating new seat: {}", dto.getSeatNumber());
        try {
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
            Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
            Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);

            Seat seat = SeatMapper.toEntity(dto, office, team, queue);
            seatRepo.save(seat);

            log.info("Seat created successfully with ID: {}", seat.getSeatId());
            return SeatMapper.toResponse(seat);
        } catch (Exception e) {
            log.error("Error creating seat: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SeatResponseDTO getById(String id) {
        log.info("Fetching seat by ID: {}", id);
        try {
            Seat seat = seatRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            return SeatMapper.toResponse(seat);
        } catch (Exception e) {
            log.error("Error fetching seat ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SeatResponseDTO> getAll() {
        log.info("Fetching all seats");
        try {
            return seatRepo.findAll()
                    .stream()
                    .map(SeatMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all seats: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SeatResponseDTO update(String id, SeatRequestDTO dto) {
        log.info("Updating seat with ID: {}", id);
        try {
            Seat existing = seatRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
            Team team = teamRepo.findById(dto.getAssignedTeamId()).orElse(null);
            Queue queue = queueRepo.findById(dto.getQueueId()).orElse(null);

            SeatMapper.updateEntity(existing, dto, office, team, queue);
            seatRepo.save(existing);

            log.info("Seat with ID {} updated successfully", id);
            return SeatMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating seat ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting seat with ID: {}", id);
        try {
            seatRepo.deleteById(id);
            log.info("Seat with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting seat ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
