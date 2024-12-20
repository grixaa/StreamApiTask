package org.studing.parsing.writer.habr.xml;

import com.thoughtworks.xstream.XStream;
import lombok.NonNull;
import lombok.val;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.wrapper.AuthorWrapper;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HabrArticleXStreamWriter implements HabrArticleXmlWriter {
    final HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(@NonNull final List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) {
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
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) {
        try {
            write(filePath, filter.getHabrArticlesLimitCountView(limitCount));
        } catch (IOException thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) {
        val xstream = new XStream();
        xstream.alias("categories", Set.class);
        xstream.alias("category", String.class);

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(filter.getUniqueCategories()));
        } catch (IOException thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) {
        try {
            write(filePath, filter.getHabrArticlesWhereTimeToReadLessThanAverage());
        } catch (IOException thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    private void write(
        @NonNull final String filePath,
        @NonNull final List<HabrArticle> articles) throws IOException {

        val xstream = new XStream();
        xstream.alias("articles", List.class);
        xstream.alias("article", HabrArticle.class);
        xstream.alias("category", String.class);
        xstream.useAttributeFor(HabrArticle.class, "categories");

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(articles));
        }
    }
}
