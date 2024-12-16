package org.studing.parsing.flie.java;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.studing.type.HabrArticle;
import org.studing.type.Performance;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PerformanceFromJson implements ParserFromJson<Performance> {
    @Override
    public List<Performance> parse(final @NonNull String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(new File(path),
                mapper.getTypeFactory().constructCollectionType(List.class, Performance.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
