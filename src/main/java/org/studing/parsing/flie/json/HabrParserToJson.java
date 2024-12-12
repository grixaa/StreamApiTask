package org.studing.parsing.flie.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HabrParserToJson implements ParserToJson<HabrArticle> {

    @Override
    public void parse(List<HabrArticle> list, String path) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(mapper.writeValueAsString(list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
