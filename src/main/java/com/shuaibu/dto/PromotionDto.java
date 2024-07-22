package com.shuaibu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {
    private Long id;
    private String classId;
    private List<Long> studentIds;
}
