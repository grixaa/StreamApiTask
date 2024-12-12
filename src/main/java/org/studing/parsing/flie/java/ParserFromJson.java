package org.studing.parsing.flie.java;

import java.util.List;

public interface ParserFromJson<T> {
    List<T> parse(String path);
}
