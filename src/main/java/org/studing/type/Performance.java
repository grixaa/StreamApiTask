package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Performance(
    String title,
    String ageLimit,
    String imageUrl,
    @JsonFormat(pattern = "HH:mm") LocalTime duration,
    @JsonFormat(pattern = "dd MMMM yyyy' в 'HH:mm") LocalDateTime date) {
}

