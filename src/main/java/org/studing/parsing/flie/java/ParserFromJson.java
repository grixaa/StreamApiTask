package org.studing.parsing.flie.java;

import lombok.NonNull;

import java.util.List;

public interface ParserFromJson<T> {
    List<T> parse(final @NonNull String path);
}
