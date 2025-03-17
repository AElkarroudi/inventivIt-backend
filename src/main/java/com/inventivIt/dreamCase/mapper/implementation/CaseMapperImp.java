package com.inventivIt.dreamCase.mapper.implementation;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;
import com.inventivIt.dreamCase.mapper.contract.CaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CaseMapperImp implements CaseMapper {

    @Override
    public CaseEntity toEntity(CaseRequestDTO requestDTO) {
        return CaseEntity.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .build();
    }

    @Override
    public CaseResponseDTO toResponseDTO(CaseEntity entity) {
        return CaseResponseDTO.builder()
                .caseId(entity.getCaseId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .lastUpdateDate(entity.getLastUpdateDate())
                .build();
    }

}
