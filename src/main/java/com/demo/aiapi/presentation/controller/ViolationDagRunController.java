package com.demo.aiapi.presentation.controller;

import com.demo.aiapi.presentation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class ViolationDagRunController {

    @Autowired
    WebClient airflowWebClient;

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleValidationException(ValidationException e) {
        return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ai-api/v1/violations")
    public Mono<DagRunsResponseDto> newViolationsDagRun(@Valid @RequestBody DagRunRequestBodyDto rqDto) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = rqDto.getStartDate(); !rqDto.getEndDate().isBefore(date); date = date.plusDays(1)) {
            dates.add(date);
        }

        return Flux.fromIterable(dates)
                .flatMap(x -> newViolationDag(x,rqDto.getComment()))       // Flux<Feed>
                .map(x -> new DagRunRsDto(x.getConf().getStartDate(),x.getDagRunId()))                  // Flux<List<Item>>
                .collectList()
                .map(DagRunsResponseDto::new);
    }

    private Mono<DagRunAirflowResponseDto> newViolationDag(LocalDate date, String comment) {
        return airflowWebClient.post()
                .uri("/api/v1/dags/process_violation/dagRuns")
                .body(Mono.just(new DagRunAirflowRequestBodyDto(date, comment)),
                        DagRunAirflowRequestBodyDto.class)
                .retrieve()
                .bodyToMono(DagRunAirflowResponseDto.class);
    }
}