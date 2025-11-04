package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.QueueRequestDTO;
import com.ait.mrbfp.dto.response.QueueResponseDTO;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.entity.Queue;
import com.ait.mrbfp.mapper.QueueMapper;
import com.ait.mrbfp.repository.OfficeRepository;
import com.ait.mrbfp.repository.QueueRepository;
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
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final OfficeRepository officeRepository;

    @Override
    public QueueResponseDTO createQueue(QueueRequestDTO dto) {
        log.info("Creating queue: {}", dto.getQueueName());

        if (queueRepository.existsByQueueName(dto.getQueueName())) {
            log.error("Queue name already exists: {}", dto.getQueueName());
            throw new RuntimeException("Queue name already exists: " + dto.getQueueName());
        }

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> {
                    log.error("Office not found: {}", dto.getOfficeId());
                    return new RuntimeException("Office not found");
                });

        Queue queue = QueueMapper.toEntity(dto, office);
        queueRepository.save(queue);

        log.info("Queue created successfully with ID: {}", queue.getQueueId());
        return QueueMapper.toResponse(queue);
    }

    @Override
    public List<QueueResponseDTO> getAllQueues() {
        log.info("Fetching all queues...");
        return queueRepository.findAll()
                .stream()
                .map(QueueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public QueueResponseDTO getQueueById(String queueId) {
        log.info("Fetching queue with ID: {}", queueId);
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> {
                    log.error("Queue not found with ID: {}", queueId);
                    return new RuntimeException("Queue not found with ID: " + queueId);
                });
        return QueueMapper.toResponse(queue);
    }

    @Override
    public List<QueueResponseDTO> getQueuesByOffice(String officeId) {
        log.info("Fetching queues for office ID: {}", officeId);
        return queueRepository.findByOffice_OfficeId(officeId)
                .stream()
                .map(QueueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public QueueResponseDTO updateQueue(String queueId, QueueRequestDTO dto) {
        log.info("Updating queue with ID: {}", queueId);

        Queue existing = queueRepository.findById(queueId)
                .orElseThrow(() -> {
                    log.error("Queue not found with ID: {}", queueId);
                    return new RuntimeException("Queue not found with ID: " + queueId);
                });

        Office office = officeRepository.findById(dto.getOfficeId())
                .orElseThrow(() -> {
                    log.error("Office not found: {}", dto.getOfficeId());
                    return new RuntimeException("Office not found");
                });

        QueueMapper.updateEntity(existing, dto, office);
        queueRepository.save(existing);

        log.info("Queue updated successfully: {}", queueId);
        return QueueMapper.toResponse(existing);
    }

    @Override
    public void deleteQueue(String queueId) {
        log.warn("Deleting queue with ID: {}", queueId);
        queueRepository.deleteById(queueId);
    }
}
