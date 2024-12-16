package org.studing.parsing.result.habr;

import lombok.NonNull;

abstract public class AbstractHabrArticleXmlWriter {
    abstract public void writeAuthorAndHisTitles(final @NonNull String filePath);

    abstract public void writeLimitCountViews(final @NonNull String filePath, final int limitCount) throws Exception;

    abstract public void writeUniqueCategories(final @NonNull String filePath);

    abstract public void writeHabrArticlesTimeToReadLessThanAverage(final @NonNull String filePath) throws Exception;
}
