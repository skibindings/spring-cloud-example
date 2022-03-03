package com.demo.aiapi.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DagRunRequestBodyDto {

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    private String comment;

    @JsonCreator
    public DagRunRequestBodyDto(
            @NotNull @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("endDate") LocalDate endDate,
            @JsonProperty("comment") String comment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    @JsonIgnore
    @AssertTrue(message = "endDate < startDate")
    public boolean isDateCorrect() {
        return !(endDate.isBefore(startDate));
    }

}
