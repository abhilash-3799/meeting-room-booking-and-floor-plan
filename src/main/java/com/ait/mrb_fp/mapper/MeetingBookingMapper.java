package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.MeetingBookingDTO;
import com.ait.mrb_fp.entity.MeetingBooking;

public class MeetingBookingMapper {

    public static MeetingBookingDTO toDTO(MeetingBooking booking) {
        if (booking == null) return null;
        return MeetingBookingDTO.builder()
                .meetingId(booking.getMeetingId())
                .roomId(booking.getRoomId())
                .bookedByEmployeeId(booking.getBookedByEmployeeId())
                .meetingDate(booking.getMeetingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .noOfAttendees(booking.getNoOfAttendees())
                .purpose(booking.getPurpose())
                .status(booking.getStatus().name())
                .isActive(booking.isActive())
                .build();
    }

    public static MeetingBooking toEntity(MeetingBookingDTO dto) {
        if (dto == null) return null;
        return MeetingBooking.builder()
                .meetingId(dto.getMeetingId())
                .roomId(dto.getRoomId())
                .bookedByEmployeeId(dto.getBookedByEmployeeId())
                .meetingDate(dto.getMeetingDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .noOfAttendees(dto.getNoOfAttendees())
                .purpose(dto.getPurpose())
                .status(MeetingBooking.Status.valueOf(dto.getStatus()))
                .isActive(dto.isActive())
                .build();
    }
}
