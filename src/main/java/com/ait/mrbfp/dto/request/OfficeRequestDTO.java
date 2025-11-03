package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OfficeRequestDTO {

    @Schema(description = "Office name", example = "Pune HQ")
    @NotBlank
    private String officeName;

    @Schema(description = "Office location", example = "Pune, India")
    @NotBlank
    private String location;

    @Schema(description = "Total seats in office", example = "120")
    @NotNull
    private Integer totalSeats;

    @Schema(description = "Is office active", example = "true")
    private boolean active = true;
}
