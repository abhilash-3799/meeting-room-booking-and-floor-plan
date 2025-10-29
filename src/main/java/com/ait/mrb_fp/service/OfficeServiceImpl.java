package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.OfficeMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
        Office office = OfficeMapper.toEntity(dto);
        office.setOfficeId("OFF" + System.currentTimeMillis()); // simple ID generator
        return OfficeMapper.toResponse(officeRepository.save(office));
    }

    @Override
    public OfficeResponseDTO getOfficeById(String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));
        return OfficeMapper.toResponse(office);
    }

    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        return officeRepository.findByIsActiveTrue()
                .stream()
                .map(OfficeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto) {
        Office existing = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

        existing.setOfficeName(dto.getOfficeName());
        existing.setLocation(dto.getLocation());
        existing.setTotalSeats(dto.getTotalSeats());

        return OfficeMapper.toResponse(officeRepository.save(existing));
    }

    @Override
    public void deactivateOffice(String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));
        office.setActive(false);
        officeRepository.save(office);
    }
}
