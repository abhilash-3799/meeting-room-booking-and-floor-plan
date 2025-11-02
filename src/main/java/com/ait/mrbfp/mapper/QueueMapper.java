package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.QueueRequestDTO;
import com.ait.mrbfp.dto.response.QueueResponseDTO;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.entity.Queue;

public class QueueMapper {

    private QueueMapper() {}

    public static Queue toEntity(QueueRequestDTO dto, Office office) {
        Queue queue = new Queue();
        queue.setOffice(office);
        queue.setQueueName(dto.getQueueName());
        queue.setTotalSeats(dto.getTotalSeats());
        queue.setActive(true);
        return queue;
    }

    public static QueueResponseDTO toResponse(Queue queue) {
        QueueResponseDTO dto = new QueueResponseDTO();
        dto.setQueueId(queue.getQueueId());
        dto.setOfficeName(queue.getOffice() != null ? queue.getOffice().getOfficeName() : null);
        dto.setQueueName(queue.getQueueName());
        dto.setTotalSeats(queue.getTotalSeats());
        dto.setActive(queue.isActive());
        return dto;
    }

    public static void updateEntity(Queue existing, QueueRequestDTO dto, Office office) {
        existing.setOffice(office);
        existing.setQueueName(dto.getQueueName());
        existing.setTotalSeats(dto.getTotalSeats());
    }
}
