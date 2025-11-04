package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    boolean existsBySeatNumber(String seatNumber);
    List<Seat> findByOffice_OfficeId(String officeId);
    List<Seat> findByQueue_QueueId(String queueId);
}
