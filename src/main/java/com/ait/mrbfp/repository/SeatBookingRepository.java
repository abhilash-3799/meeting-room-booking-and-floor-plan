package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    List<SeatBooking> findByEmployee_EmployeeId(String employeeId);
    List<SeatBooking> findBySeat_SeatId(String seatId);
}
