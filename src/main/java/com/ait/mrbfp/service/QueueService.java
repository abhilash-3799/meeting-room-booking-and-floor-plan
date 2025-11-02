package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.QueueRequestDTO;
import com.ait.mrbfp.dto.response.QueueResponseDTO;

import java.util.List;

public interface QueueService {

    QueueResponseDTO createQueue(QueueRequestDTO dto);

    List<QueueResponseDTO> getAllQueues();

    QueueResponseDTO getQueueById(String queueId);

    List<QueueResponseDTO> getQueuesByOffice(String officeId);

    QueueResponseDTO updateQueue(String queueId, QueueRequestDTO dto);

    void deleteQueue(String queueId);
}
