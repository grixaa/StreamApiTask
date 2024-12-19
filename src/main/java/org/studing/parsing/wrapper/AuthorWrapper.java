package org.studing.parsing.wrapper;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@XStreamAlias("author")
public class AuthorWrapper {
    @XStreamAsAttribute
    String name;

    @XStreamImplicit
    List<ArticleWrapper> articles;
}