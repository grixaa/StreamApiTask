package org.studing.parsing.reader;

import lombok.NonNull;
import org.studing.type.Performance;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PerformanceJsonReader extends JsonReader<Performance> {
    @Override
    public List<Performance> parse(@NonNull final String path) throws IOException {
        try {
            return mapper.readValue(
                new File(path),
                mapper.getTypeFactory().constructCollectionType(List.class, Performance.class));
        } catch (IOException thrown) {
            System.err.println("Failed to read json file: " + thrown);
            throw thrown;
        }
    }
}
