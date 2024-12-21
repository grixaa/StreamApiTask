package org.studing.parsing.writer.habr.json;

import lombok.NonNull;
import lombok.val;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HabrArticleJsonWriter extends JsonWriter<HabrArticle> {
    @Override
    public void parse(@NonNull final List<HabrArticle> list, @NonNull final String path) throws IOException {
        try (val writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(mapper.writeValueAsString(list));
        } catch (final IOException thrown) {
            System.err.println("Failed to write json file: " + thrown);
            throw thrown;
        }
    }
}
