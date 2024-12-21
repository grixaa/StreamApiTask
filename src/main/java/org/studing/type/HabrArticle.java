package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record HabrArticle(
    String title,
    String author,
    String timeToRead,
    String countViews,
    String imageUrl,
    List<String> categories,
    String textPreview,
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime datePublished) {
}
