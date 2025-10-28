package com.ait.mrb_fp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeRequestDTO {
    private String officeName;
    private String location;
    private int totalSeats;
}
