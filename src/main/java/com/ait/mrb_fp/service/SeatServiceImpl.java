package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.SeatRequestDTO;
import com.ait.mrb_fp.dto.response.SeatResponseDTO;
import com.ait.mrb_fp.entity.*;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.SeatMapper;
import com.ait.mrb_fp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("Creating seat for office ID: {}", dto.getOfficeId());
        try {
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

            Team team = dto.getAssignedTeamId() != null
                    ? teamRepo.findById(dto.getAssignedTeamId()).orElse(null)
                    : null;

            Queue queue = dto.getQueueId() != null
                    ? queueRepo.findById(dto.getQueueId()).orElse(null)
                    : null;

            Seat seat = SeatMapper.toEntity(dto, office, team, queue);
            //seat.setSeatId("SEAT" + System.currentTimeMillis());

            seatRepo.save(seat);
            log.info("Seat created successfully with ID: {}", seat.getSeatId());
            return SeatMapper.toResponse(seat);
        } catch (ResourceNotFoundException ex) {
            log.error("Failed to create seat: {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while creating seat: {}", ex.getMessage());
            throw new RuntimeException("Database operation failed while creating seat: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while creating seat: {}", ex.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public SeatResponseDTO getById(String id) {
        log.info("Fetching seat by ID: {}", id);
        try {
            Seat seat = seatRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            return SeatMapper.toResponse(seat);
        } catch (ResourceNotFoundException ex) {
            log.error("Seat not found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching seat by ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error fetching seat: " + ex.getMessage());
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
        } catch (DataAccessException ex) {
            log.error("Database error while fetching seats: {}", ex.getMessage());
            throw new RuntimeException("Database error fetching seats: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error fetching seats: {}", ex.getMessage());
            throw new RuntimeException("Unexpected error fetching seats: " + ex.getMessage());
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

            Team team = dto.getAssignedTeamId() != null
                    ? teamRepo.findById(dto.getAssignedTeamId()).orElse(null)
                    : null;

            Queue queue = dto.getQueueId() != null
                    ? queueRepo.findById(dto.getQueueId()).orElse(null)
                    : null;

            SeatMapper.updateEntity(existing, dto, office, team, queue);
            seatRepo.save(existing);

            log.info("Seat updated successfully: {}", id);
            return SeatMapper.toResponse(existing);
        } catch (ResourceNotFoundException ex) {
            log.error("Failed to update seat: {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while updating seat {}: {}", id, ex.getMessage());
            throw new RuntimeException("Database operation failed while updating seat: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while updating seat {}: {}", id, ex.getMessage());
            throw new RuntimeException("Unexpected error updating seat: " + ex.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting seat with ID: {}", id);
        try {
            if (!seatRepo.existsById(id)) {
                throw new ResourceNotFoundException("Seat not found with ID: " + id);
            }
            seatRepo.deleteById(id);
            log.info("Seat deleted successfully with ID: {}", id);
        } catch (ResourceNotFoundException ex) {
            log.error("Failed to delete seat: {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while deleting seat {}: {}", id, ex.getMessage());
            throw new RuntimeException("Database operation failed while deleting seat: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while deleting seat {}: {}", id, ex.getMessage());
            throw new RuntimeException("Unexpected error deleting seat: " + ex.getMessage());
        }
    }
}
