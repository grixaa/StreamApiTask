package org.studing.parsing.writer.habr.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.studing.exception.json.JsonWriteException;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class JsonWriter<T> {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected static final Logger logger = getLogger(JsonWriter.class);

    static {
        mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.configure(INDENT_OUTPUT, true);
    }

    abstract void parse(List<T> list, String path) throws JsonWriteException;
}
