package org.studing.parsing.reader;

import lombok.NonNull;
import org.studing.exception.json.JsonReadException;
import org.studing.type.HabrArticle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HabrArticleJsonReader extends JsonReader<HabrArticle> {
    @Override
    public List<HabrArticle> parse(@NonNull final String path) throws JsonReadException {
        try {
            return mapper.readValue(
                new File(path),
                mapper.getTypeFactory().constructCollectionType(List.class, HabrArticle.class));
        } catch (IOException thrown) {
            throw new JsonReadException("Failed to read json file at path: %s".formatted(path), thrown);
        }
    }
}