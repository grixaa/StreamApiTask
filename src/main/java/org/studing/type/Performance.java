package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Performance {
    public static final DateFormat FORMAT_DATE = new SimpleDateFormat("dd MMMM yyyy' в 'HH:mm", new Locale("ru"));
    public static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));

    private String title;

    @JsonFormat(pattern = "dd MMMM yyyy' в 'HH:mm", timezone = "UTC")
    private Date date;
    private String duration;
    private String ageLimit;
    private String imageUrl;

    @Override
    public String toString() {
        return "Performance{" + '\n' +
               "title=" + title + '\n' +
               ", date=" + FORMAT_DATE.format(date) + '\n' +
               ", duration=" + duration + '\n' +
               ", ageLimit=" + ageLimit + '\n' +
               ", imageUrl=" + imageUrl + '\n' +
               '}';
    }
}

