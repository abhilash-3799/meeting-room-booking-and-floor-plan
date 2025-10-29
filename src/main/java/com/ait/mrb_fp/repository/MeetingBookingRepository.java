package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingBookingRepository extends JpaRepository<MeetingRoomBooking, String> {
}
