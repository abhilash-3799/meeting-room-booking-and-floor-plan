package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.OfficeRequestDTO;
import com.ait.mrbfp.dto.response.OfficeResponseDTO;

import java.util.List;

public interface OfficeService {

    OfficeResponseDTO createOffice(OfficeRequestDTO requestDTO);

    List<OfficeResponseDTO> getAllOffices();

    OfficeResponseDTO getOfficeById(String officeId);

    OfficeResponseDTO updateOffice(String officeId, OfficeRequestDTO requestDTO);

    void deleteOffice(String officeId);
}
