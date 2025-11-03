package com.ait.mrb_fp.exception;

/**
 *  Thrown when a meeting room with a given ID doesn’t exist.
 * Example: Booking room "R001" → room not found.
 */
public class MeetingRoomNotFoundException extends ResourceNotFoundException {
    public MeetingRoomNotFoundException(String roomId) {
        super("Meeting room not found with ID: " + roomId);
    }
}
