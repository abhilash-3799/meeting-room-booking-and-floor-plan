package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.OfficeRequestDTO;
import com.ait.mrbfp.dto.response.OfficeResponseDTO;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.mapper.OfficeMapper;
import com.ait.mrbfp.repository.OfficeRepository;
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
    public OfficeResponseDTO createOffice(OfficeRequestDTO requestDTO) {
        log.info("Creating office: {}", requestDTO.getOfficeName());

        if (officeRepository.existsByOfficeName(requestDTO.getOfficeName())) {
            log.error("Duplicate office name: {}", requestDTO.getOfficeName());
            throw new RuntimeException("Office name already exists: " + requestDTO.getOfficeName());
        }

        Office office = OfficeMapper.toEntity(requestDTO);
        office = officeRepository.save(office);
        log.info("Office created with ID: {}", office.getOfficeId());
        return OfficeMapper.toResponse(office);
    }

    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        log.info("Fetching all offices...");
        return officeRepository.findAll()
                .stream()
                .map(OfficeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OfficeResponseDTO getOfficeById(String officeId) {
        log.info("Fetching office with ID: {}", officeId);
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Office not found with ID: " + officeId));
        return OfficeMapper.toResponse(office);
    }

    @Override
    public OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO requestDTO) {
        log.info("Updating office with ID: {}", officeId);

        Office existing = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Office not found with ID: " + officeId));

        if (!existing.getOfficeName().equals(requestDTO.getOfficeName())
                && officeRepository.existsByOfficeName(requestDTO.getOfficeName())) {
            log.error("Duplicate office name during update: {}", requestDTO.getOfficeName());
            throw new RuntimeException("Office name already exists: " + requestDTO.getOfficeName());
        }

        OfficeMapper.updateEntity(existing, requestDTO);
        officeRepository.save(existing);
        log.info("Office updated successfully: {}", existing.getOfficeId());
        return OfficeMapper.toResponse(existing);
    }

    @Override
    public void deleteOffice(String officeId) {
        log.warn("Deleting office with ID: {}", officeId);
        officeRepository.deleteById(officeId);
    }
}
