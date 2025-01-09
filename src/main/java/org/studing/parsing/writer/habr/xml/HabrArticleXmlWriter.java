package org.studing.parsing.writer.habr.xml;

public interface HabrArticleXmlWriter {
    void writeAuthorAndHisTitles(String filePath) throws Exception;

    void writeLimitCountViews(String filePath, final int limitCount) throws Exception;

    void writeUniqueCategories(String filePath) throws Exception;

    void writeTimeToReadLessThanAverage(String filePath) throws Exception;
}
