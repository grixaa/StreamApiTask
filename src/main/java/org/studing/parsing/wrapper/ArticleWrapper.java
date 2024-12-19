package org.studing.parsing.wrapper;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@XStreamAlias("article")
public class ArticleWrapper {
    @XStreamAlias("title")
    String title;

    @XStreamAlias("textPreview")
    String textPreview;
}