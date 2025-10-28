package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.entity.Queue;
import com.ait.mrb_fp.entity.Office;
import java.time.LocalDateTime;

public class QueueMapper {

    public static Queue toEntity(QueueRequestDTO dto, Office office) {
        return Queue.builder()
                .office(office)
                .queueName(dto.getQueueName())
                .totalSeats(dto.getTotalSeats())
                .createdDate(LocalDateTime.now())
                .isActive(true)
                .build();
    }

    public static QueueResponseDTO toResponse(Queue entity) {
        return QueueResponseDTO.builder()
                .queueId(entity.getQueueId())
                .queueName(entity.getQueueName())
                .officeName(entity.getOffice().getOfficeName())
                .totalSeats(entity.getTotalSeats())
                .createdDate(entity.getCreatedDate().toString())
                .isActive(entity.isActive())
                .build();
    }
}
