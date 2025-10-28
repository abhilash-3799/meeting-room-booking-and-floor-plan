package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.MeetingBookingRequestDTO;
import com.ait.mrb_fp.dto.response.MeetingBookingResponseDTO;
import com.ait.mrb_fp.entity.MeetingBooking;
import com.ait.mrb_fp.entity.MeetingRoom;
import com.ait.mrb_fp.entity.Employee;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MeetingBookingMapper {

    public static MeetingBooking toEntity(MeetingBookingRequestDTO dto, MeetingRoom room, Employee bookedBy) {
        return MeetingBooking.builder()
                .room(room)
                .bookedBy(bookedBy)
                .meetingDate(LocalDate.parse(dto.getMeetingDate()))
                .startTime(LocalDateTime.parse(dto.getStartTime()))
                .endTime(LocalDateTime.parse(dto.getEndTime()))
                .noOfAttendees(dto.getNoOfAttendees())
                .purpose(dto.getPurpose())
                .status(MeetingBooking.MeetingStatus.valueOf(dto.getStatus()))
                .isActive(true)
                .build();
    }

    public static MeetingBookingResponseDTO toResponse(MeetingBooking entity) {
        return MeetingBookingResponseDTO.builder()
                .meetingId(entity.getMeetingId())
                .roomName(entity.getRoom().getRoomName())
                .bookedBy(entity.getBookedBy().getFirstName() + " " + entity.getBookedBy().getLastName())
                .meetingDate(entity.getMeetingDate().toString())
                .startTime(entity.getStartTime().toString())
                .endTime(entity.getEndTime().toString())
                .noOfAttendees(entity.getNoOfAttendees())
                .purpose(entity.getPurpose())
                .status(entity.getStatus().name())
                .isActive(entity.isActive())
                .build();
    }
}
