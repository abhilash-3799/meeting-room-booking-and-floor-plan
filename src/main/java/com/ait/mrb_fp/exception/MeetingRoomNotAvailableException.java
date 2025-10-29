package com.ait.mrb_fp.exception;

/**
 * ⚠️ Thrown when a meeting room is already booked or unavailable.
 * Example: Booking room for overlapping time slot.
 */
public class MeetingRoomNotAvailableException extends BadRequestException {
    public MeetingRoomNotAvailableException(String message) {
        super("Meeting room not available: " + message);
    }
}
