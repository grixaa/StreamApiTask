package org.studing.parsing.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthorWrapper {
    String name;
    List<String> titles;
}
