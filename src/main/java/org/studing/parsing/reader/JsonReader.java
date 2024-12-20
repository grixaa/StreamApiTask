package org.studing.parsing.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;

public abstract class JsonReader<T> {
    protected static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    abstract List<T> parse(@NonNull final String path);
}
