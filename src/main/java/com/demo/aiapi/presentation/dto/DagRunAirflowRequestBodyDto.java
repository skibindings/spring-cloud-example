package com.demo.aiapi.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DagRunAirflowRequestBodyDto {

    private DagRunAirflowConfDto conf;

    public DagRunAirflowRequestBodyDto(LocalDate date, String comment) {
        conf = new DagRunAirflowConfDto(date, comment);
    }
}
