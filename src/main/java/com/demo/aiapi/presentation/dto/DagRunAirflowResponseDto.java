package com.demo.aiapi.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DagRunAirflowResponseDto {

    private DagRunAirflowConfDto conf;

    private String dagRunId;

    @JsonCreator
    public DagRunAirflowResponseDto(
            @JsonProperty("dag_run_id") String dagRunId,
            @JsonProperty("conf") DagRunAirflowConfDto conf
            ) {
        this.dagRunId = dagRunId;
        this.conf = conf;
    }
}
