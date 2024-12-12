package org.studing.parsing.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthorWrapper {
    private String name;
    private List<String> titles;
}
