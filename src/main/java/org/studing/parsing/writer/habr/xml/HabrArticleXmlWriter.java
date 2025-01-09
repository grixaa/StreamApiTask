package org.studing.parsing.writer.habr.xml;

import org.studing.exception.XmlWriteException;

public interface HabrArticleXmlWriter {
    void writeAuthorAndHisTitles(String filePath) throws XmlWriteException;

    void writeLimitCountViews(String filePath, final int limitCount) throws XmlWriteException;

    void writeUniqueCategories(String filePath) throws XmlWriteException;

    void writeTimeToReadLessThanAverage(String filePath) throws XmlWriteException;
}
