package org.studing.parsing.flie.java;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.studing.type.HabrArticle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HabrParserFromJson implements ParserFromJson<HabrArticle> {

    @Override
    public List<HabrArticle> parse(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(new File(path),
                    mapper.getTypeFactory().constructCollectionType(List.class, HabrArticle.class));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}