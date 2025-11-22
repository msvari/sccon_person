package com.poc.ex.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorDTO (@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss") LocalDateTime dateTime,
                       String code,
                       String status,
                       List<String> errors) {
}
