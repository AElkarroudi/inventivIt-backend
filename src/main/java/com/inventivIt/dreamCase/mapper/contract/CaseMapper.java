package com.inventivIt.dreamCase.mapper.contract;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;

public interface CaseMapper {

    CaseEntity toEntity(CaseRequestDTO requestDTO);
    CaseResponseDTO toResponseDTO(CaseEntity entity);

}
