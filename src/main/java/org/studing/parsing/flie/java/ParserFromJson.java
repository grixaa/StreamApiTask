package org.studing.parsing.flie.java;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.List;

public abstract class ParserFromJson<T> {
    static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    abstract List<T> parse(final @NonNull String path);
}
