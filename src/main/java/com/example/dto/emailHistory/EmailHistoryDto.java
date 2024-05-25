package com.example.dto.emailHistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDto {
    @NotBlank
    private Integer id;

    @NotBlank
    private String message;

    @NotBlank
    private String email;

    @NotBlank
    private LocalDateTime createdDate = LocalDateTime.now();
}
