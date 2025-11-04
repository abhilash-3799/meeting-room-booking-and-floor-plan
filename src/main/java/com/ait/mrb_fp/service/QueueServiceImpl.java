package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Queue;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.QueueMapper;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.repository.QueueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepo;
    private final OfficeRepository officeRepo;

    public QueueServiceImpl(QueueRepository queueRepo, OfficeRepository officeRepo) {
        this.queueRepo = queueRepo;
        this.officeRepo = officeRepo;
    }

    @Override
    public QueueResponseDTO create(QueueRequestDTO dto) {
        log.info("Creating queue for office ID: {}", dto.getOfficeId());
        try {
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
            Queue queue = QueueMapper.toEntity(dto, office);
            //queue.setQueueId("Q" + System.currentTimeMillis());
            queueRepo.save(queue);
            log.info("Queue created successfully with ID: {}", queue.getQueueId());
            return QueueMapper.toResponse(queue);
        } catch (ResourceNotFoundException ex) {
            log.error("Failed to create queue - {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while creating queue: {}", ex.getMessage());
            throw new RuntimeException("Database error while creating queue.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while creating queue: {}", ex.getMessage());
            throw new RuntimeException("Transaction failed while creating queue.");
        } catch (Exception ex) {
            log.error("Unexpected error while creating queue: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public QueueResponseDTO getById(String id) {
        log.info("Fetching queue by ID: {}", id);
        try {
            Queue queue = queueRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
            log.info("Queue found with ID: {}", id);
            return QueueMapper.toResponse(queue);
        } catch (ResourceNotFoundException ex) {
            log.error("Queue not found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching queue ID {}: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<QueueResponseDTO> getAll() {
        log.info("Fetching all queues");
        try {
            List<QueueResponseDTO> queues = queueRepo.findAll()
                    .stream()
                    .map(QueueMapper::toResponse)
                    .collect(Collectors.toList());
            log.info("Total queues found: {}", queues.size());
            return queues;
        } catch (DataAccessException ex) {
            log.error("Database error while fetching queues: {}", ex.getMessage());
            throw new RuntimeException("Database error while fetching queues.");
        } catch (Exception ex) {
            log.error("Unexpected error fetching queues: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public QueueResponseDTO update(String id, QueueRequestDTO dto) {
        log.info("Updating queue with ID: {}", id);
        try {
            Queue existing = queueRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
            QueueMapper.updateEntity(existing, dto, office);
            queueRepo.save(existing);
            log.info("Queue updated successfully with ID: {}", id);
            return QueueMapper.toResponse(existing);
        } catch (ResourceNotFoundException ex) {
            log.error("Update failed - {}", ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Database error while updating queue {}: {}", id, ex.getMessage());
            throw new RuntimeException("Database error while updating queue.");
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while updating queue {}: {}", id, ex.getMessage());
            throw new RuntimeException("Transaction failed while updating queue.");
        } catch (Exception ex) {
            log.error("Unexpected error updating queue {}: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void delete(String id) {
        log.warn("Deleting queue with ID: {}", id);
        try {
            queueRepo.deleteById(id);
            log.info("Queue deleted successfully with ID: {}", id);
        } catch (DataAccessException ex) {
            log.error("Database error while deleting queue {}: {}", id, ex.getMessage());
            throw new RuntimeException("Database error while deleting queue.");
        } catch (Exception ex) {
            log.error("Unexpected error deleting queue {}: {}", id, ex.getMessage());
            throw ex;
        }
    }
}
