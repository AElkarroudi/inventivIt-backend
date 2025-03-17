package com.inventivIt.dreamCase.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseResponseDTO implements Serializable {

    private Long caseId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateDate;

}
