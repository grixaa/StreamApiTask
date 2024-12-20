package org.studing.parsing.writer.habr.xml;

import lombok.NonNull;

public interface HabrArticleXmlWriter {
    void writeAuthorAndHisTitles(@NonNull final String filePath);

    void writeLimitCountViews(@NonNull final String filePath, final int limitCount);

    void writeUniqueCategories(@NonNull final String filePath);

    void writeTimeToReadLessThanAverage(@NonNull final String filePath);
}
