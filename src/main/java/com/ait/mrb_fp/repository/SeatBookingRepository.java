package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    boolean existsBySeat_SeatIdAndAllocationDate(String seatId, LocalDateTime allocationDate);

}
