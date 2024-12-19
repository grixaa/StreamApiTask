package org.studing.parsing.writer.habr.xml;

import lombok.NonNull;

abstract public class AbstractHabrArticleXmlWriter {
    abstract public void writeAuthorAndHisTitles(@NonNull final String filePath);

    abstract public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws Exception;

    abstract public void writeUniqueCategories(@NonNull final String filePath);

    abstract public void writeHabrArticlesTimeToReadLessThanAverage(@NonNull final String filePath) throws Exception;
}
