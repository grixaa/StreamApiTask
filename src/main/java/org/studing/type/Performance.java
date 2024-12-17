package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Performance {
    String title;
    String duration;
    String ageLimit;
    String imageUrl;

    @JsonFormat(pattern = "dd MMMM yyyy' в 'HH:mm", timezone = "UTC")
    Date date;
}

