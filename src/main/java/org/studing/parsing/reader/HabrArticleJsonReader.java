package org.studing.parsing.reader;

import lombok.NonNull;
import org.studing.type.HabrArticle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HabrArticleJsonReader extends JsonReader<HabrArticle> {
    @Override
    public List<HabrArticle> parse(final @NonNull String path) {
        try {
            return mapper.readValue(
                new File(path),
                mapper.getTypeFactory().constructCollectionType(List.class, HabrArticle.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
