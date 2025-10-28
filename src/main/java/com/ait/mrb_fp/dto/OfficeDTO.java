package com.ait.mrb_fp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeDTO {
    private String officeId;
    private String officeName;
    private String location;
    private int totalSeats;
    private boolean isActive;

    private List<SeatDTO> seats;
    private List<EmployeeDTO> employees;
}
