package com.ait.mrb_fp.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeRequestDTO {

    @NotBlank(message = "Office name is required")
    private String officeName;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 300, message = "Total seats cannot exceed 300")
    private int totalSeats;
}

