package com.si_pmu.pmu.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PayloadErrorResponse {
    private Integer errorCode;
    private String message;
}
