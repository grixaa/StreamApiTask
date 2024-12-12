package org.studing.parsing.result.habr;

abstract public class AbstractHabrArticleXmlWriter {
    abstract public void writeAuthorAndHisTitles(String filePath);

    abstract public void writeLimitCountViews(String filePath, int limitCount) throws Exception;

    abstract public void writeUniqueCategories(String filePath);

    abstract public void writeHabrArticlesTimeToReadLessThanAverage(String filePath) throws Exception;
}
