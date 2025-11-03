package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Seat;
import com.ait.mrb_fp.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

    boolean existsBySeat_SeatIdAndAllocationDate(String seatId, LocalDateTime allocationDate);

    boolean existsByEmployee_EmployeeId(String employeeId);


    boolean existsByEmployee_EmployeeIdAndSeat_SeatId(String employeeId, String seatId);

    boolean existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(String employeeId, String seatId, LocalDate localDate);



//    @Query("SELECT CASE WHEN COUNT(sb) > 0 THEN TRUE ELSE FALSE END " +
//            "FROM SeatBooking sb WHERE sb.seat.seatId = :seatId AND FUNCTION('DATE', sb.allocationDate) = :date")
//    boolean existsBySeatAndDate(@Param("seatId") String seatId, @Param("date") LocalDate date);






    boolean existsBySeat_SeatIdAndAllocationDateAndEmployee_EmployeeIdNot(
            String seatId,
            LocalDateTime allocationDate,
            String employeeId
    );

    boolean existsByEmployee_EmployeeIdAndSeat_SeatIdAndAllocationDate(
            String employeeId,
            String seatId,
            LocalDateTime allocationDate
    );



    @Query("SELECT CASE WHEN COUNT(sb) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SeatBooking sb WHERE sb.seat.seatId = :seatId " +
            "AND sb.allocationDate BETWEEN :startOfDay AND :endOfDay")
    boolean existsBySeatAndDateRange(@Param("seatId") String seatId,
                                     @Param("startOfDay") LocalDateTime startOfDay,
                                     @Param("endOfDay") LocalDateTime endOfDay);


}
