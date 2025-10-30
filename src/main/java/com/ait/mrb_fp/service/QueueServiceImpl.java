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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepo;
    private final OfficeRepository officeRepo;

    public QueueServiceImpl(QueueRepository queueRepo, OfficeRepository officeRepo) {
        this.queueRepo = queueRepo;
        this.officeRepo = officeRepo;
    }

    @Override
    public QueueResponseDTO create(QueueRequestDTO dto) {
        log.info("Creating queue: {}", dto.getQueueName());
        try {
            Office office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found"));
            Queue queue = QueueMapper.toEntity(dto, office);
            queueRepo.save(queue);
            log.info("Queue created successfully with ID: {}", queue.getQueueId());
            return QueueMapper.toResponse(queue);
        } catch (Exception e) {
            log.error("Error creating queue: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public QueueResponseDTO getById(String id) {
        log.info("Fetching queue with ID: {}", id);
        try {
            Queue queue = queueRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));
            return QueueMapper.toResponse(queue);
        } catch (Exception e) {
            log.error("Error fetching queue ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<QueueResponseDTO> getAll() {
        log.info("Fetching all queues");
        try {
            return queueRepo.findAll().stream().map(QueueMapper::toResponse).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching queue list: {}", e.getMessage());
            throw e;
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
            log.info("Queue with ID {} updated successfully", id);
            return QueueMapper.toResponse(existing);
        } catch (Exception e) {
            log.error("Error updating queue ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting queue with ID: {}", id);
        try {
            queueRepo.deleteById(id);
            log.info("Queue with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting queue ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
