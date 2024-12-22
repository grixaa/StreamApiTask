package org.studing.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.studing.parsing.writer.habr.converter.CategoriesListConverter;

import java.time.LocalDateTime;
import java.util.List;

@XStreamAlias("article")
public record HabrArticle(
    String title,
    String author,
    String timeToRead,
    String countViews,
    String imageUrl,
    @XStreamConverter(CategoriesListConverter.class) List<String> categories,
    String textPreview,
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime datePublished) {
}
