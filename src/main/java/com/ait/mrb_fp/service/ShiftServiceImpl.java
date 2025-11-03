package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.ShiftMapper;
import com.ait.mrb_fp.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Override
    public ShiftResponseDTO createShift(ShiftRequestDTO dto) {
        log.info("Attempting to create shift: {}", dto.getShiftName());
        try {
            if (dto == null) throw new InvalidRequestBodyException("Shift request body cannot be null.");
            if (dto.getShiftName() == null || dto.getShiftName().isBlank())
                throw new MissingRequestParameterException("Shift name is required.");
            if (dto.getStartTime() == null || dto.getEndTime() == null)
                throw new MissingRequestParameterException("Start and End time are required for a shift.");

            boolean exists = shiftRepository.existsByShiftName(dto.getShiftName());
            if (exists) throw new DuplicateResourceException("Shift already exists: " + dto.getShiftName());

            Shift shift = ShiftMapper.toEntity(dto);
            shift.setShiftId("SFT" + System.currentTimeMillis());
            shift.setActive(true);

            Shift saved = shiftRepository.save(shift);
            log.info("Shift created successfully with ID: {}", saved.getShiftId());
            return ShiftMapper.toResponse(saved);

        } catch (DataAccessException ex) {
            log.error("Database error while creating shift: {}", ex.getMessage());
            throw new DatabaseException("Database error occurred while creating shift.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while creating shift: {}", ex.getMessage());
            throw new TransactionFailedException("Transaction failed while creating shift.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error while creating shift: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ShiftResponseDTO getShiftById(String shiftId) {
        log.info("Fetching shift with ID: {}", shiftId);
        try {
            if (shiftId == null || shiftId.isBlank())
                throw new MissingRequestParameterException("Shift ID must not be empty.");

            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            if (!shift.isActive())
                throw new InvalidStateException("Shift is inactive. Reactivate before accessing details.");

            return ShiftMapper.toResponse(shift);
        } catch (ResourceNotFoundException | InvalidStateException ex) {
            log.error("Error fetching shift: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching shift {}: {}", shiftId, ex.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public List<ShiftResponseDTO> getAllShifts() {
        log.info("Fetching all active shifts");
        try {
            return shiftRepository.findByIsActiveTrue()
                    .stream()
                    .map(ShiftMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            log.error("Database error while fetching shifts: {}", ex.getMessage());
            throw new DatabaseException("Error occurred while fetching shifts from database.");
        } catch (Exception ex) {
            log.error("Unexpected error while fetching all shifts: {}", ex.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override
    public ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO dto) {
        log.info("Updating shift with ID: {}", shiftId);
        try {
            if (shiftId == null || shiftId.isBlank())
                throw new MissingRequestParameterException("Shift ID is required for update.");
            if (dto == null)
                throw new InvalidRequestBodyException("Shift update request cannot be null.");

            Shift existing = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            if (!existing.isActive())
                throw new InvalidStateException("Cannot update an inactive shift.");

            Shift duplicate = shiftRepository.findByShiftName(dto.getShiftName());
            if (duplicate != null && !duplicate.getShiftId().equals(shiftId))
                throw new DuplicateResourceException("Another shift already exists with name: " + dto.getShiftName());

            existing.setShiftName(dto.getShiftName());
            existing.setStartTime(dto.getStartTime());
            existing.setEndTime(dto.getEndTime());
            existing.setDescription(dto.getDescription());

            Shift saved = shiftRepository.save(existing);
            log.info("Shift updated successfully: {}", saved.getShiftId());
            return ShiftMapper.toResponse(saved);

        } catch (DataAccessException ex) {
            log.error("Database error while updating shift {}: {}", shiftId, ex.getMessage());
            throw new DatabaseException("Database error occurred while updating shift.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed during shift update {}: {}", shiftId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed during shift update.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error while updating shift {}: {}", shiftId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deactivateShift(String shiftId) {
        log.warn("Deactivating shift with ID: {}", shiftId);
        try {
            if (shiftId == null || shiftId.isBlank())
                throw new MissingRequestParameterException("Shift ID is required for deactivation.");

            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            if (!shift.isActive())
                throw new InvalidStateException("Shift is already inactive.");

            shift.setActive(false);
            shiftRepository.save(shift);
            log.info("Shift deactivated successfully: {}", shiftId);

        } catch (DataAccessException ex) {
            log.error("Database error while deactivating shift {}: {}", shiftId, ex.getMessage());
            throw new DatabaseException("Database error occurred while deactivating shift.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while deactivating shift {}: {}", shiftId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed while deactivating shift.");
        } catch (RuntimeException ex) {
            log.error("Unexpected error while deactivating shift {}: {}", shiftId, ex.getMessage());
            throw ex;
        }
    }
}
