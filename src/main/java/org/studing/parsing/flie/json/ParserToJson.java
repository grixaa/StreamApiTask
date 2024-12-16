package org.studing.parsing.flie.json;

import lombok.NonNull;

import java.util.List;

public interface ParserToJson<T> {
    void parse(final @NonNull List<T> list, final @NonNull String path);
}
