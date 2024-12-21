package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    String title;
    String ageLimit;
    String imageUrl;

    @JsonFormat(pattern = "HH:mm")
    LocalTime duration;

    @JsonFormat(pattern = "dd MMMM yyyy' в 'HH:mm")
    LocalDateTime date;
}

