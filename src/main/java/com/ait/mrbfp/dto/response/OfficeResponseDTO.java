package com.ait.mrbfp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OfficeResponseDTO {

    private String officeId;
    private String officeName;
    private String location;
    private int totalSeats;
    private boolean active;
}
