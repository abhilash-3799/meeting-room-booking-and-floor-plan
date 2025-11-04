package com.ait.mrbfp.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeatBookingResponseDTO {

    private String allocationId;
    private String seatNumber;
    private String employeeFirstName;
    private String employeeId;
    private LocalDateTime allocationDate;
    private String status;
    private boolean active;

}
