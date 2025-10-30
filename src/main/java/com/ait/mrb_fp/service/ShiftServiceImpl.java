package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.ShiftMapper;
import com.ait.mrb_fp.repository.ShiftRepository;
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
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Override
    public ShiftResponseDTO createShift(ShiftRequestDTO dto) {
        log.info("Creating new shift: {}", dto.getShiftName());
        try {
            Shift shift = ShiftMapper.toEntity(dto);
            shift.setShiftId("SFT" + System.currentTimeMillis());
            Shift saved = shiftRepository.save(shift);
            log.info("Shift created successfully with ID: {}", saved.getShiftId());
            return ShiftMapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Error creating shift '{}': {}", dto.getShiftName(), e.getMessage());
            throw e;
        }
    }

    @Override
    public ShiftResponseDTO getShiftById(String shiftId) {
        log.info("Fetching shift by ID: {}", shiftId);
        try {
            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));
            return ShiftMapper.toResponse(shift);
        } catch (Exception e) {
            log.error("Error fetching shift ID {}: {}", shiftId, e.getMessage());
            throw e;
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
        } catch (Exception e) {
            log.error("Error fetching shift list: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO requestDTO) {
        log.info("Updating shift with ID: {}", shiftId);
        try {
            Shift existing = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

            existing.setShiftName(requestDTO.getShiftName());
            existing.setStartTime(requestDTO.getStartTime());
            existing.setEndTime(requestDTO.getEndTime());
            existing.setDescription(requestDTO.getDescription());

            Shift updated = shiftRepository.save(existing);
            log.info("Shift with ID {} updated successfully", shiftId);
            return ShiftMapper.toResponse(updated);
        } catch (Exception e) {
            log.error("Error updating shift ID {}: {}", shiftId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deactivateShift(String shiftId) {
        log.info("Deactivating shift with ID: {}", shiftId);
        try {
            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));
            shift.setActive(false);
            shiftRepository.save(shift);
            log.info("Shift with ID {} deactivated successfully", shiftId);
        } catch (Exception e) {
            log.error("Error deactivating shift ID {}: {}", shiftId, e.getMessage());
            throw e;
        }
    }
}
