package org.studing.parsing.flie.json;

import java.util.List;

public interface ParserToJson<T> {
    void parse(List<T> list, String path);
}
