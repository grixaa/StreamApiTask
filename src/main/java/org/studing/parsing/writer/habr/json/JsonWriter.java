package org.studing.parsing.writer.habr.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public abstract class JsonWriter<T> {
    protected static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.configure(INDENT_OUTPUT, true);
    }

    abstract void parse(@NonNull final List<T> list, @NonNull final String path);
}
