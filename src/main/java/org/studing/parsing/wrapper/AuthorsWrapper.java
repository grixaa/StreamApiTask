package org.studing.parsing.wrapper;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@XStreamAlias("authors")
public class AuthorsWrapper {
    @XStreamImplicit
    List<AuthorWrapper> authors;
}