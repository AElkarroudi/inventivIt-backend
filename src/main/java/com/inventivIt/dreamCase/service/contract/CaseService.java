package com.inventivIt.dreamCase.service.contract;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CaseService {

    List<CaseResponseDTO> findAll();
    Page<CaseResponseDTO> findAll(Pageable pageable);

    CaseEntity findById(Long id);
    CaseResponseDTO findByIdAndMapToResponseDTO(Long id);

    CaseResponseDTO save(CaseRequestDTO requestDTO);
    CaseResponseDTO update(Long id, CaseRequestDTO requestDTO);
    void delete(Long id);

}
