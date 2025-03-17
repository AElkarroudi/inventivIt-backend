package com.inventivIt.dreamCase.mapper.implementation;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.entity.CaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CaseMapperImpTest {

    private CaseMapperImp caseMapper;

    @BeforeEach
    void setUp() {
        caseMapper = new CaseMapperImp();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        String title = "Test Case";
        String description = "This is a test case description";

        CaseRequestDTO requestDTO = CaseRequestDTO.builder()
                .caseId(1L)
                .title(title)
                .description(description)
                .build();

        CaseEntity result = caseMapper.toEntity(requestDTO);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
    }

    @Test
    void toResponseDTO_shouldMapAllFields() {
        Long caseId = 1L;
        String title = "Test Case";
        String description = "This is a test case description";
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime lastUpdateDate = LocalDateTime.now();

        CaseEntity entity = CaseEntity.builder()
                .caseId(caseId)
                .title(title)
                .description(description)
                .createdAt(createdAt)
                .lastUpdateDate(lastUpdateDate)
                .build();

        CaseResponseDTO result = caseMapper.toResponseDTO(entity);

        assertNotNull(result);
        assertEquals(caseId, result.getCaseId());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(lastUpdateDate, result.getLastUpdateDate());
    }

}