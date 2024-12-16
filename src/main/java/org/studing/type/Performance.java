package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Performance {
    public static final DateFormat FORMAT_DATE = new SimpleDateFormat("dd MMMM yyyy' в 'HH:mm", new Locale("ru"));
    public static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));

    @JsonFormat(pattern = "dd MMMM yyyy' в 'HH:mm", timezone = "UTC")
    Date date;
    String title;
    String duration;
    String ageLimit;
    String imageUrl;
}

