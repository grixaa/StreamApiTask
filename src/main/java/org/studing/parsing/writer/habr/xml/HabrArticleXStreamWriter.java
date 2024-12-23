package org.studing.parsing.writer.habr.xml;

import com.thoughtworks.xstream.XStream;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.wrapper.AuthorWrapper;
import org.studing.parsing.writer.habr.converter.CategoriesListConverter;
import org.studing.parsing.writer.habr.converter.HabrArticlesListConverter;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(makeFinal = true)
public class HabrArticleXStreamWriter implements HabrArticleXmlWriter {
    HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(@NonNull final List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) throws Exception {
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
            System.err.println("Failed to write author and his Habr article titles to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws Exception {
        try {
            write(filePath, filter.getHabrArticlesLimitCountView(limitCount));
        } catch (IOException thrown) {
            System.err.println("Failed to write HabrArticle limit count view to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) throws Exception {
        val xstream = new XStream();
        xstream.registerConverter(new CategoriesListConverter(true));

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(filter.getUniqueCategories()));
        } catch (IOException thrown) {
            System.err.println("Failed to write HabrArticle unique categories to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) throws Exception {
        try {
            write(filePath, filter.getHabrArticlesWhereTimeToReadLessThanAverage());
        } catch (IOException thrown) {
            System.err.println("Failed to write HabrArticle with time to read less then average to: " + filePath);
            throw thrown;
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
