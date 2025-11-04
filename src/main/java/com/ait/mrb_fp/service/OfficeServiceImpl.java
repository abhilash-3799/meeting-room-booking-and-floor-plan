package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.OfficeRequestDTO;
import com.ait.mrb_fp.dto.response.OfficeResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.OfficeMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Override
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
        log.info("Creating office with name: {}", dto != null ? dto.getOfficeName() : "null");
        try {
            if (dto == null) {
                throw new InvalidRequestBodyException("Office request body cannot be null.");
            }

            if (dto.getOfficeName() == null || dto.getOfficeName().isBlank()) {
                throw new MissingRequestParameterException("Office name is required.");
            }

            if (dto.getLocation() == null || dto.getLocation().isBlank()) {
                throw new MissingRequestParameterException("Office location is required.");
            }

            boolean exists = officeRepository.existsByOfficeName(dto.getOfficeName());
            if (exists) {
                throw new DuplicateResourceException("Office with name already exists: " + dto.getOfficeName());
            }

            Office office = OfficeMapper.toEntity(dto);
            //office.setOfficeId("OFF" + System.currentTimeMillis());
            office.setActive(true);

            log.info("Saving new office to database: {}", office.getOfficeName());
            return OfficeMapper.toResponse(officeRepository.save(office));

        } catch (DataAccessException ex) {
            log.error("Database error while creating office: {}", ex.getMessage());
            throw new DatabaseException("Database error occurred while creating office.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while creating office: {}", ex.getMessage());
            throw new TransactionFailedException("Transaction failed while creating office.");
        } catch (Exception ex) {
            log.error("Unexpected error while creating office: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public OfficeResponseDTO getOfficeById(String officeId) {
        log.info("Fetching office by ID: {}", officeId);
        try {
            if (officeId == null || officeId.isBlank()) {
                throw new MissingRequestParameterException("Office ID cannot be empty.");
            }

            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            if (!office.isActive()) {
                throw new InvalidStateException("Office is inactive. Reactivate before accessing details.");
            }

            log.info("Office found with ID: {}", officeId);
            return OfficeMapper.toResponse(office);
        } catch (DataAccessException ex) {
            log.error("Database error while fetching office ID {}: {}", officeId, ex.getMessage());
            throw new DatabaseException("Error fetching office from database.");
        } catch (Exception ex) {
            log.error("Unexpected error fetching office ID {}: {}", officeId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<OfficeResponseDTO> getAllOffices() {
        log.info("Fetching all active offices");
        try {
            List<OfficeResponseDTO> offices = officeRepository.findByIsActiveTrue()
                    .stream()
                    .map(OfficeMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Total active offices fetched: {}", offices.size());
            return offices;
        } catch (DataAccessException ex) {
            log.error("Database error while fetching offices: {}", ex.getMessage());
            throw new DatabaseException("Error fetching offices from database.");
        } catch (Exception ex) {
            log.error("Unexpected error fetching offices: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO dto) {
        log.info("Updating office with ID: {}", officeId);
        try {
            if (officeId == null || officeId.isBlank()) {
                throw new MissingRequestParameterException("Office ID is required for update.");
            }

            if (dto == null) {
                throw new InvalidRequestBodyException("Office update request cannot be null.");
            }

            Office existing = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            if (!existing.isActive()) {
                throw new InvalidStateException("Cannot update an inactive office.");
            }

            Office duplicate = officeRepository.findByOfficeName(dto.getOfficeName());
            if (duplicate != null && !duplicate.getOfficeId().equals(officeId)) {
                throw new DuplicateResourceException("Another office already exists with name: " + dto.getOfficeName());
            }

            existing.setOfficeName(dto.getOfficeName());
            existing.setLocation(dto.getLocation());
            existing.setTotalSeats(dto.getTotalSeats());

            log.info("Saving updated office with ID: {}", officeId);
            return OfficeMapper.toResponse(officeRepository.save(existing));

        } catch (DataAccessException ex) {
            log.error("Database error while updating office {}: {}", officeId, ex.getMessage());
            throw new DatabaseException("Database error occurred while updating office.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while updating office {}: {}", officeId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed during office update.");
        } catch (Exception ex) {
            log.error("Unexpected error updating office {}: {}", officeId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deactivateOffice(String officeId) {
        log.warn("Deactivating office with ID: {}", officeId);
        try {
            if (officeId == null || officeId.isBlank()) {
                throw new MissingRequestParameterException("Office ID is required for deactivation.");
            }

            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + officeId));

            if (!office.isActive()) {
                throw new InvalidStateException("Office is already inactive.");
            }

            office.setActive(false);
            officeRepository.save(office);
            log.info("Office with ID {} successfully deactivated", officeId);
        } catch (DataAccessException ex) {
            log.error("Database error while deactivating office {}: {}", officeId, ex.getMessage());
            throw new DatabaseException("Error occurred while deactivating office in database.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while deactivating office {}: {}", officeId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed while deactivating office.");
        } catch (Exception ex) {
            log.error("Unexpected error deactivating office {}: {}", officeId, ex.getMessage());
            throw ex;
        }
    }
}
