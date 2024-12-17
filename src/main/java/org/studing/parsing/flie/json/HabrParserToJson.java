package org.studing.parsing.flie.json;

import lombok.NonNull;
import lombok.val;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HabrParserToJson extends ParserToJson<HabrArticle> {
    @Override
    public void parse(final @NonNull List<HabrArticle> list, final @NonNull String path) {
        try (val writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(mapper.writeValueAsString(list));
        } catch (IOException thrown) {
            throw new RuntimeException(thrown);
        }
    }
}
