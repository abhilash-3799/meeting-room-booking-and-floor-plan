package com.ait.mrbfp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OfficeRequestDTO {

    @NotBlank
    private String officeName;

    @NotBlank
    private String location;

    @NotNull
    private Integer totalSeats;

    private boolean active = true;
}
