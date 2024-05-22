package com.example.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfileResponseDto<T> {
    private List<T> content;
    private Long totalCount;
}
