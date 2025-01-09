package org.studing.parsing.writer.habr.xml;

import com.thoughtworks.xstream.XStream;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.slf4j.Logger;
import org.studing.exception.XmlWriteException;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.writer.habr.converter.CategoriesListConverter;
import org.studing.parsing.writer.habr.converter.HabrArticlesListConverter;
import org.studing.parsing.writer.habr.wrapper.AuthorWrapper;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@FieldDefaults(makeFinal = true)
public class HabrArticleXStreamWriter implements HabrArticleXmlWriter {
    private static final Logger logger = getLogger(HabrArticleXStreamWriter.class);
    HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(@NonNull final List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) throws XmlWriteException {
        val xstream = new XStream();
        xstream.alias("authors", List.class);
        xstream.alias("author", AuthorWrapper.class);

        xstream.alias("title", String.class);
        xstream.useAttributeFor(AuthorWrapper.class, "titles");

        val authorsList = new ArrayList<>();
        filter.getAuthorAndHisTitles()
            .forEach((k, v) -> authorsList.add(new AuthorWrapper(k, v)));

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(authorsList));
        } catch (IOException thrown) {
            logger.error("Failed to write author and his Habr article titles to path: {}", filePath, thrown);
            throw new XmlWriteException(thrown);
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws XmlWriteException {
        try {
            write(filePath, filter.getHabrArticlesLimitCountView(limitCount));
        } catch (IOException thrown) {
            logger.error("Failed to write HabrArticle limit count view to path: {}", filePath, thrown);
            throw new XmlWriteException(thrown);
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) throws XmlWriteException {
        val xstream = new XStream();
        xstream.registerConverter(new CategoriesListConverter(true));

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(filter.getUniqueCategories()));
        } catch (IOException thrown) {
            logger.error("Failed to write HabrArticle unique categories to path: {}", filePath, thrown);
            throw new XmlWriteException(thrown);
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) throws XmlWriteException {
        try {
            write(filePath, filter.getHabrArticlesWhereTimeToReadLessThanAverage());
        } catch (IOException thrown) {
            logger.error(
                "Failed to write HabrArticle with time to read less then average to path: {}",
                filePath,
                thrown);
            throw new XmlWriteException(thrown);
        }
    }

    private void write(
        @NonNull final String filePath,
        @NonNull final List<HabrArticle> articles) throws IOException {

        val xstream = new XStream();
        xstream.registerConverter(new HabrArticlesListConverter());
        xstream.processAnnotations(HabrArticle.class);
        xstream.alias("articles", List.class);
        xstream.alias("article", HabrArticle.class);

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(articles));
        }
    }
}
