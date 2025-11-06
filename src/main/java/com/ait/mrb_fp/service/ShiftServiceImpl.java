package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.ShiftMapper;
import com.ait.mrb_fp.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;


    @Override
    public ShiftResponseDTO createShift(ShiftRequestDTO dto) {
        try {
            if (dto == null) {
                throw new InvalidRequestBodyException("Shift request body cannot be null.");
            }

            if (dto.getShiftName() == null || dto.getShiftName().isBlank()) {
                throw new MissingRequestParameterException("Shift name is required.");
            }

            if (dto.getStartTime() == null || dto.getEndTime() == null) {
                throw new MissingRequestParameterException("Start and End time are required for a shift.");
            }


            boolean exists = shiftRepository.existsByShiftName(dto.getShiftName());
            if (exists) {
                throw new DuplicateResourceException("Shift with name already exists: " + dto.getShiftName());
            }

            Shift shift = ShiftMapper.toEntity(dto);

            shift.setActive(true);

            return ShiftMapper.toResponse(shiftRepository.save(shift));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while creating shift.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while creating shift.");
        }
    }


    @Override
    public ShiftResponseDTO getShiftById(String shiftId) {
        if (shiftId == null || shiftId.isBlank()) {
            throw new MissingRequestParameterException("Shift ID must not be empty.");
        }

        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

        if (!shift.isActive()) {
            throw new InvalidStateException("Shift is inactive. Reactivate before accessing details.");
        }

        return ShiftMapper.toResponse(shift);
    }


    @Override
    public List<ShiftResponseDTO> getAllShifts() {
        try {
            return shiftRepository.findByIsActiveTrue()
                    .stream()
                    .map(ShiftMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Error occurred while fetching shifts from database.");
        }
    }


    @Override
    public ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO dto) {
        try {
            if (shiftId == null || shiftId.isBlank()) {
                throw new MissingRequestParameterException("Shift ID is required for update.");
            }

            if (dto == null) {
                throw new InvalidRequestBodyException("Shift update request cannot be null.");
            }

            Shift existing = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            if (!existing.isActive()) {
                throw new InvalidStateException("Cannot update an inactive shift.");
            }


            Shift duplicate = shiftRepository.findByShiftName(dto.getShiftName());
            if (duplicate != null && !duplicate.getShiftId().equals(shiftId)) {
                throw new DuplicateResourceException("Another shift already exists with name: " + dto.getShiftName());
            }

            existing.setShiftName(dto.getShiftName());
            existing.setStartTime(dto.getStartTime());
            existing.setEndTime(dto.getEndTime());
            existing.setDescription(dto.getDescription());

            return ShiftMapper.toResponse(shiftRepository.save(existing));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while updating shift.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed during shift update.");
        }
    }


    @Override
    public void deactivateShift(String shiftId) {
        try {
            if (shiftId == null || shiftId.isBlank()) {
                throw new MissingRequestParameterException("Shift ID is required for deactivation.");
            }

            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            if (!shift.isActive()) {
                throw new InvalidStateException("Shift is already inactive.");
            }

            shift.setActive(false);
            shiftRepository.save(shift);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred while deactivating shift.");
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while deactivating shift.");
        }
    }
}
