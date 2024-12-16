package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabrArticle {
    public static final DateFormat FORMAT_DATE_PUBLISHED = new SimpleDateFormat("yyyy-MM-dd, HH:mm");

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "UTC")
    Date datePublished;
    String title;
    String author;
    String timeToRead;
    String countViews;
    String imageUrl;
    List<String> categories;
    String textPreview;
}
