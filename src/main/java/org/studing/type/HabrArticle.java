package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabrArticle {
    String title;
    String author;
    String timeToRead;
    String countViews;
    String imageUrl;
    List<String> categories;
    String textPreview;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "UTC")
    Date datePublished;
}
