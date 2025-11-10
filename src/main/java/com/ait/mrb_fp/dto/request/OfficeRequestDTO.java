package com.ait.mrb_fp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeRequestDTO {

    @NotBlank(message = "Office name is required")
    private String officeName;

    @NotBlank(message = "Location required")
    private String location;

    @NotBlank(message = "Total Seats required")
    private int totalSeats;
}
