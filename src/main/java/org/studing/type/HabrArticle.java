package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HabrArticle {
    public static final DateFormat FORMAT_DATE_PUBLISHED = new SimpleDateFormat("yyyy-MM-dd, HH:mm");

    private String title;
    private String author;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "UTC")
    private Date datePublished;

    private String timeToRead;
    private String countViews;
    private String imageUrl;
    private List<String> categories;
    private String textPreview;

    @Override
    public String toString() {
        return "HabrArticle{" +
               "title=" + title + '\n' +
               ", author=" + author + '\n' +
               ", datePublished=" + FORMAT_DATE_PUBLISHED.format(datePublished) + '\n' +
               ", timeToRead=" + timeToRead + '\n' +
               ", countViews=" + countViews + '\n' +
               ", imageUrl=" + imageUrl + '\n' +
               ", categories=" + categories + '\n' +
               ", textPreview=" + textPreview + '\n' +
               '}';
    }
}
