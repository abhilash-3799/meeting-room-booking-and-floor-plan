package com.ait.mrbfp.mapper;

import com.ait.mrbfp.dto.request.MeetingRoomBookingRequestDTO;
import com.ait.mrbfp.dto.response.MeetingRoomBookingResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.MeetingRoom;
import com.ait.mrbfp.entity.MeetingRoomBooking;

public class MeetingRoomBookingMapper {

    private MeetingRoomBookingMapper() {
    }

    public static MeetingRoomBooking toEntity(MeetingRoomBookingRequestDTO dto, MeetingRoom room, Employee employee) {
        MeetingRoomBooking booking = new MeetingRoomBooking();
        booking.setRoom(room);
        booking.setBookedBy(employee);
        booking.setMeetingDate(dto.getMeetingDate());
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setPurpose(dto.getPurpose());
        booking.setStatus(MeetingRoomBooking.MeetingStatus.BOOKED);
        booking.setActive(true);
        return booking;
    }

    public static MeetingRoomBookingResponseDTO toResponse(MeetingRoomBooking booking) {
        MeetingRoomBookingResponseDTO dto = new MeetingRoomBookingResponseDTO();
        dto.setMeetingId(booking.getMeetingId());
        dto.setRoomName(booking.getRoom() != null ? booking.getRoom().getRoomName() : null);
        dto.setBookedByEmployeeName(booking.getBookedBy() != null
                ? booking.getBookedBy().getFirstName() + " " + booking.getBookedBy().getLastName()
                : null);
        dto.setMeetingDate(booking.getMeetingDate());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setPurpose(booking.getPurpose());
        dto.setStatus(booking.getStatus().name());
        dto.setActive(booking.isActive());
        return dto;
    }

    public static void updateEntity(MeetingRoomBooking existing, MeetingRoomBookingRequestDTO dto,
                                    MeetingRoom room, Employee employee) {
        existing.setRoom(room);
        existing.setBookedBy(employee);
        existing.setMeetingDate(dto.getMeetingDate());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setPurpose(dto.getPurpose());
    }
}
