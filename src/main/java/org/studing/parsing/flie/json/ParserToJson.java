package org.studing.parsing.flie.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public abstract class ParserToJson<T> {
    static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.configure(INDENT_OUTPUT, true);
    }

    abstract void parse(final @NonNull List<T> list, final @NonNull String path);
}
