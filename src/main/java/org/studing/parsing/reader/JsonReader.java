package org.studing.parsing.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class JsonReader<T> {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected static final Logger logger = getLogger(JsonReader.class);

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    abstract List<T> parse(String path) throws IOException;
}
