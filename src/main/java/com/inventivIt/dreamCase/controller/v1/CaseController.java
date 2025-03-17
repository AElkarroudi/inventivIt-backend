package com.inventivIt.dreamCase.controller.v1;

import com.inventivIt.dreamCase.dto.request.CaseRequestDTO;
import com.inventivIt.dreamCase.dto.response.CaseResponseDTO;
import com.inventivIt.dreamCase.service.contract.CaseService;
import com.inventivIt.dreamCase.util.apiResponse.success.SuccessDTO;
import com.inventivIt.dreamCase.util.validationGroup.OnCreate;
import com.inventivIt.dreamCase.util.validationGroup.OnUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cases")
public class CaseController {

    private final CaseService caseService;

    @GetMapping("/all")
    public ResponseEntity<SuccessDTO<List<CaseResponseDTO>>> findAll() {
        log.info("Fetching all cases");
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        caseService.findAll()
                )
        );
    }

    @GetMapping
    public ResponseEntity<SuccessDTO<Page<CaseResponseDTO>>> pageableFindAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "title"));

        log.info("Fetching cases with pagination - Page: {}, Size: {}, Sort: {}", page, size, sortDirection);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        caseService.findAll(pageable)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDTO<CaseResponseDTO>> findById(
            @PathVariable Long id
    ) {
        log.info("Fetching case with ID: {}", id);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        caseService.findByIdAndMapToResponseDTO(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<SuccessDTO<CaseResponseDTO>> save(
            @Validated(OnCreate.class) @RequestBody CaseRequestDTO requestDTO
    ) {
        log.info("Creating a new case: {}", requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDTO<>(caseService.save(requestDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessDTO<CaseResponseDTO>> update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody CaseRequestDTO requestDTO
    ) {
        log.info("Updating case with ID: {} - Data: {}", id, requestDTO);
        return ResponseEntity.ok(
                new SuccessDTO<>(
                        caseService.update(id, requestDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        log.info("Deleting case with ID: {}", id);
        caseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
