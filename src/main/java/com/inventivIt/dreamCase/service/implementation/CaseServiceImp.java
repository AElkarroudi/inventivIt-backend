package com.inventivIt.dreamCase.service.implementation;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;
import com.inventivIt.dreamCase.mapper.contract.CaseMapper;
import com.inventivIt.dreamCase.repository.CaseRepository;
import com.inventivIt.dreamCase.service.contract.CaseService;
import com.inventivIt.dreamCase.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseServiceImp implements CaseService {

    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;

    @Override
    public List<CaseResponseDTO> findAll() {
        List<CaseResponseDTO> cases = caseRepository.findAll()
                .stream()
                .map(caseMapper::toResponseDTO)
                .collect(Collectors.toList());

        log.info("Retrieved {} cases successfully", cases.size());
        return cases;
    }

    @Override
    public Page<CaseResponseDTO> findAll(Pageable pageable) {
        log.info("Retrieving cases with pagination - Page Size: {}, Page Number: {}", pageable.getPageSize(), pageable.getPageNumber());
        return caseRepository.findAll(pageable).map(caseMapper::toResponseDTO);
    }

    @Override
    public CaseEntity findById(Long id) {
        log.info("Searching for case with ID: {}", id);
        return caseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Case not found with ID: {}", id);
                    return new ResourceNotFoundException("Case not found with ID: " + id);
                });
    }

    @Override
    public CaseResponseDTO findByIdAndMapToResponseDTO(Long id) {
        return caseMapper.toResponseDTO(findById(id));
    }

    @Override
    @Transactional
    public CaseResponseDTO save(CaseRequestDTO requestDTO) {
        log.info("Creating a new case - Title: {}, Description: {}", requestDTO.getTitle(), requestDTO.getDescription());
        CaseEntity savedCase = caseRepository.save(caseMapper.toEntity(requestDTO));
        return caseMapper.toResponseDTO(savedCase);
    }

    @Override
    @Transactional
    public CaseResponseDTO update(Long id, CaseRequestDTO requestDTO) {
        log.info("Updating case with ID: {}", id);

        CaseEntity caseToUpdate = findById(id);
        caseToUpdate.setTitle(requestDTO.getTitle());
        caseToUpdate.setDescription(requestDTO.getDescription());

        CaseEntity updatedCase = caseRepository.save(caseToUpdate);
        return caseMapper.toResponseDTO(updatedCase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting case with ID: {}", id);
        CaseEntity caseToDelete = findById(id);
        caseRepository.delete(caseToDelete);
    }

}
