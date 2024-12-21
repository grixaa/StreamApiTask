package org.studing.parsing.writer.habr.xml;

import lombok.NonNull;

public interface HabrArticleXmlWriter {
    void writeAuthorAndHisTitles(@NonNull final String filePath) throws Exception;

    void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws Exception;

    void writeUniqueCategories(@NonNull final String filePath) throws Exception;

    void writeTimeToReadLessThanAverage(@NonNull final String filePath) throws Exception;
}
