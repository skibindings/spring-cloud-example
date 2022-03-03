package com.demo.aiapi.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DagRunsResponseDto {

    @JsonProperty("dagRuns")
    private List<DagRunRsDto> dagRuns;

    public DagRunsResponseDto(List<DagRunRsDto> dagRuns) {
        this.dagRuns = dagRuns;
    }
}
