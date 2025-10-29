package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.ShiftRequestDTO;
import com.ait.mrb_fp.dto.response.ShiftResponseDTO;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.ShiftMapper;
import com.ait.mrb_fp.repository.ShiftRepository;
import com.ait.mrb_fp.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        Shift shift = ShiftMapper.toEntity(dto);
        shift.setShiftId("SFT" + System.currentTimeMillis()); // simple ID generator
        return ShiftMapper.toResponse(shiftRepository.save(shift));
    }

    @Override
    public ShiftResponseDTO getShiftById(String shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));
        return ShiftMapper.toResponse(shift);
    }

    @Override
    public List<ShiftResponseDTO> getAllShifts() {
        return shiftRepository.findByIsActiveTrue()
                .stream()
                .map(ShiftMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftResponseDTO updateShift(String shiftId, ShiftRequestDTO requestDTO) {
        Shift existing = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

        existing.setShiftName(requestDTO.getShiftName());
        existing.setStartTime(requestDTO.getStartTime());
        existing.setEndTime(requestDTO.getEndTime());
        existing.setDescription(requestDTO.getDescription());

        return ShiftMapper.toResponse(shiftRepository.save(existing));
    }

    @Override
    public void deactivateShift(String shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));
        shift.setActive(false);
        shiftRepository.save(shift);
    }
}
