package com.inventivIt.dreamCase.dto.request;

import com.inventivIt.dreamCase.util.validationGroup.OnCreate;
import com.inventivIt.dreamCase.util.validationGroup.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseRequestDTO implements Serializable {

    @NotNull(
            groups = OnUpdate.class,
            message = "Case id is required"
    )
    Long caseId;

    @NotBlank(
            message = "Title cannot be blank",
            groups = {OnUpdate.class, OnCreate.class}
    )
    @Size(
            max = 255,
            groups = {OnUpdate.class, OnCreate.class},
            message = "Title cannot exceed 255 characters"
    )
    String title;

    @NotBlank(
            message = "Description cannot be blank",
            groups = {OnUpdate.class, OnCreate.class}
    )
    @Size(
            max = 2056,
            groups = {OnUpdate.class, OnCreate.class},
            message = "Description cannot exceed 2056 characters"
    )
    String description;

}
