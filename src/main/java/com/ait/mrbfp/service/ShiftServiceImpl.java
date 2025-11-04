package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.ShiftRequestDTO;
import com.ait.mrbfp.dto.response.ShiftResponseDTO;
import com.ait.mrbfp.entity.Shift;
import com.ait.mrbfp.mapper.ShiftMapper;
import com.ait.mrbfp.repository.ShiftRepository;
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
    public ShiftResponseDTO createShift(ShiftRequestDTO requestDTO) {
        log.info("Creating shift: {}", requestDTO.getShiftName());
        if (shiftRepository.existsByShiftName(requestDTO.getShiftName())) {
            log.error("Duplicate shift name: {}", requestDTO.getShiftName());
            throw new RuntimeException("Shift name already exists: " + requestDTO.getShiftName());
        }
        Shift shift = ShiftMapper.toEntity(requestDTO);
        shift = shiftRepository.save(shift);
        log.info("Shift created with ID: {}", shift.getShiftId());
        return ShiftMapper.toResponse(shift);
    }

    @Override
    public List<ShiftResponseDTO> getAllShifts() {
        log.info("Fetching all shifts...");
        return shiftRepository.findAll()
                .stream()
                .map(ShiftMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftResponseDTO getShiftById(String shiftId) {
        log.info("Fetching shift with ID: {}", shiftId);
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        return ShiftMapper.toResponse(shift);
    }

    @Override
    public ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO requestDTO) {
        log.info("Updating shift with ID: {}", shiftId);
        Shift existing = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        if (!existing.getShiftName().equals(requestDTO.getShiftName())
                && shiftRepository.existsByShiftName(requestDTO.getShiftName())) {
            log.error("Duplicate shift name during update: {}", requestDTO.getShiftName());
            throw new RuntimeException("Shift name already exists: " + requestDTO.getShiftName());
        }
        ShiftMapper.updateEntity(existing, requestDTO);
        shiftRepository.save(existing);
        log.info("Shift updated successfully: {}", existing.getShiftId());
        return ShiftMapper.toResponse(existing);
    }

    @Override
    public void deleteShift(String shiftId) {
        log.warn("Deleting shift with ID: {}", shiftId);
        shiftRepository.deleteById(shiftId);
    }
}
