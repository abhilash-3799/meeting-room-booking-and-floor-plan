package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.MeetingRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomBookingRepository extends JpaRepository<MeetingRoomBooking, String> { }
