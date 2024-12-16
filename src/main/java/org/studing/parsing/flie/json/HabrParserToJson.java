package org.studing.parsing.flie.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public class HabrParserToJson implements ParserToJson<HabrArticle> {

    @Override
    public void parse(final @NonNull List<HabrArticle> list, final @NonNull String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.configure(INDENT_OUTPUT, true);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(mapper.writeValueAsString(list));
        } catch (IOException thrown) {
            throw new RuntimeException(thrown);
        }
    }
}
