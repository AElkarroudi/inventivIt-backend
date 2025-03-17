package com.inventivIt.dreamCase.service.implementation;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;
import com.inventivIt.dreamCase.mapper.contract.CaseMapper;
import com.inventivIt.dreamCase.repository.CaseRepository;
import com.inventivIt.dreamCase.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaseServiceImpTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private CaseMapper caseMapper;

    @InjectMocks
    private CaseServiceImp caseService;

    private CaseEntity caseEntity1;
    private CaseEntity caseEntity2;
    private CaseResponseDTO responseDTO1;
    private CaseResponseDTO responseDTO2;
    private CaseRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        caseEntity1 = CaseEntity.builder()
                .caseId(1L)
                .title("Test Case 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now().minusDays(1))
                .lastUpdateDate(LocalDateTime.now())
                .build();

        caseEntity2 = CaseEntity.builder()
                .caseId(2L)
                .title("Test Case 2")
                .description("Description 2")
                .createdAt(LocalDateTime.now().minusDays(2))
                .lastUpdateDate(LocalDateTime.now())
                .build();

        responseDTO1 = CaseResponseDTO.builder()
                .caseId(1L)
                .title("Test Case 1")
                .description("Description 1")
                .createdAt(caseEntity1.getCreatedAt())
                .lastUpdateDate(caseEntity1.getLastUpdateDate())
                .build();

        responseDTO2 = CaseResponseDTO.builder()
                .caseId(2L)
                .title("Test Case 2")
                .description("Description 2")
                .createdAt(caseEntity2.getCreatedAt())
                .lastUpdateDate(caseEntity2.getLastUpdateDate())
                .build();

        requestDTO = CaseRequestDTO.builder()
                .title("New Case")
                .description("New Description")
                .build();
    }

    @Test
    void findAll_shouldReturnAllCases() {
        List<CaseEntity> caseEntities = Arrays.asList(caseEntity1, caseEntity2);
        when(caseRepository.findAll()).thenReturn(caseEntities);
        when(caseMapper.toResponseDTO(caseEntity1)).thenReturn(responseDTO1);
        when(caseMapper.toResponseDTO(caseEntity2)).thenReturn(responseDTO2);

        List<CaseResponseDTO> result = caseService.findAll();

        assertEquals(2, result.size());
        assertEquals(responseDTO1, result.get(0));
        assertEquals(responseDTO2, result.get(1));
        verify(caseRepository).findAll();
        verify(caseMapper, times(2)).toResponseDTO(any(CaseEntity.class));
    }

    @Test
    void findAllWithPageable_shouldReturnPagedCases() {
        Pageable pageable = PageRequest.of(0, 10);
        List<CaseEntity> caseEntities = Arrays.asList(caseEntity1, caseEntity2);
        Page<CaseEntity> casePage = new PageImpl<>(caseEntities, pageable, caseEntities.size());

        when(caseRepository.findAll(pageable)).thenReturn(casePage);
        when(caseMapper.toResponseDTO(caseEntity1)).thenReturn(responseDTO1);
        when(caseMapper.toResponseDTO(caseEntity2)).thenReturn(responseDTO2);

        Page<CaseResponseDTO> result = caseService.findAll(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(responseDTO1, result.getContent().get(0));
        assertEquals(responseDTO2, result.getContent().get(1));
        verify(caseRepository).findAll(pageable);
        verify(caseMapper, times(2)).toResponseDTO(any(CaseEntity.class));
    }

    @Test
    void findById_shouldReturnCaseWhenExists() {
        Long id = 1L;
        when(caseRepository.findById(id)).thenReturn(Optional.of(caseEntity1));

        CaseEntity result = caseService.findById(id);

        assertEquals(caseEntity1, result);
        verify(caseRepository).findById(id);
    }

    @Test
    void findById_shouldThrowExceptionWhenCaseNotFound() {
        Long id = 999L;
        when(caseRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> caseService.findById(id)
        );

        assertEquals("Case not found with ID: " + id, exception.getMessage());
        verify(caseRepository).findById(id);
    }

    @Test
    void findByIdAndMapToResponseDTO_shouldReturnMappedCase() {
        Long id = 1L;
        when(caseRepository.findById(id)).thenReturn(Optional.of(caseEntity1));
        when(caseMapper.toResponseDTO(caseEntity1)).thenReturn(responseDTO1);

        CaseResponseDTO result = caseService.findByIdAndMapToResponseDTO(id);

        assertEquals(responseDTO1, result);
        verify(caseRepository).findById(id);
        verify(caseMapper).toResponseDTO(caseEntity1);
    }

    @Test
    void save_shouldCreateNewCase() {
        CaseEntity newEntity = CaseEntity.builder()
                .title("New Case")
                .description("New Description")
                .build();

        CaseEntity savedEntity = CaseEntity.builder()
                .caseId(3L)
                .title("New Case")
                .description("New Description")
                .createdAt(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        CaseResponseDTO savedResponseDTO = CaseResponseDTO.builder()
                .caseId(3L)
                .title("New Case")
                .description("New Description")
                .createdAt(savedEntity.getCreatedAt())
                .lastUpdateDate(savedEntity.getLastUpdateDate())
                .build();

        when(caseMapper.toEntity(requestDTO)).thenReturn(newEntity);
        when(caseRepository.save(newEntity)).thenReturn(savedEntity);
        when(caseMapper.toResponseDTO(savedEntity)).thenReturn(savedResponseDTO);

        CaseResponseDTO result = caseService.save(requestDTO);

        assertEquals(savedResponseDTO, result);
        verify(caseMapper).toEntity(requestDTO);
        verify(caseRepository).save(newEntity);
        verify(caseMapper).toResponseDTO(savedEntity);
    }

    @Test
    void update_shouldUpdateExistingCase() {
        Long id = 1L;
        CaseRequestDTO updateDTO = CaseRequestDTO.builder()
                .title("Updated Title")
                .description("Updated Description")
                .build();

        CaseEntity updatedEntity = CaseEntity.builder()
                .caseId(1L)
                .title("Updated Title")
                .description("Updated Description")
                .createdAt(caseEntity1.getCreatedAt())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        CaseResponseDTO updatedResponseDTO = CaseResponseDTO.builder()
                .caseId(1L)
                .title("Updated Title")
                .description("Updated Description")
                .createdAt(updatedEntity.getCreatedAt())
                .lastUpdateDate(updatedEntity.getLastUpdateDate())
                .build();

        when(caseRepository.findById(id)).thenReturn(Optional.of(caseEntity1));
        when(caseRepository.save(any(CaseEntity.class))).thenReturn(updatedEntity);
        when(caseMapper.toResponseDTO(updatedEntity)).thenReturn(updatedResponseDTO);

        CaseResponseDTO result = caseService.update(id, updateDTO);

        assertEquals(updatedResponseDTO, result);
        verify(caseRepository).findById(id);
        verify(caseRepository).save(any(CaseEntity.class));
        verify(caseMapper).toResponseDTO(updatedEntity);
    }

    @Test
    void delete_shouldDeleteExistingCase() {
        Long id = 1L;
        when(caseRepository.findById(id)).thenReturn(Optional.of(caseEntity1));
        doNothing().when(caseRepository).delete(caseEntity1);

        caseService.delete(id);

        verify(caseRepository).findById(id);
        verify(caseRepository).delete(caseEntity1);
    }

}