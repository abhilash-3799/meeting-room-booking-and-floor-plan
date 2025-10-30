package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.OfficeMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
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
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
        log.info("Creating new office: {}", dto.getOfficeName());
        try {
            Office office = OfficeMapper.toEntity(dto);
            office.setOfficeId("OFF" + System.currentTimeMillis());
            Office saved = officeRepository.save(office);
            log.info("Office created successfully with ID: {}", saved.getOfficeId());
            return OfficeMapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Error creating office: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public OfficeResponseDTO getOfficeById(String officeId) {
        log.info("Fetching office by ID: {}", officeId);
        try {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));
            return OfficeMapper.toResponse(office);
        } catch (Exception e) {
            log.error("Error fetching office ID {}: {}", officeId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        log.info("Fetching all active offices");
        try {
            return officeRepository.findByIsActiveTrue()
                    .stream()
                    .map(OfficeMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching office list: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto) {
        log.info("Updating office with ID: {}", officeId);
        try {
            Office existing = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            existing.setOfficeName(dto.getOfficeName());
            existing.setLocation(dto.getLocation());
            existing.setTotalSeats(dto.getTotalSeats());
            Office updated = officeRepository.save(existing);
            log.info("Office with ID {} updated successfully", officeId);
            return OfficeMapper.toResponse(updated);
        } catch (Exception e) {
            log.error("Error updating office ID {}: {}", officeId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deactivateOffice(String officeId) {
        log.info("Deactivating office with ID: {}", officeId);
        try {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));
            office.setActive(false);
            officeRepository.save(office);
            log.info("Office with ID {} marked as inactive", officeId);
        } catch (Exception e) {
            log.error("Error deactivating office ID {}: {}", officeId, e.getMessage());
            throw e;
        }
    }
}
