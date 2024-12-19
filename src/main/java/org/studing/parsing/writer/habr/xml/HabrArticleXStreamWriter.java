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

public class HabrArticleXStreamWriter extends AbstractHabrArticleXmlWriter {
    final HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(final @NonNull List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(final @NonNull String filePath) {
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
            throw new RuntimeException(thrown);
        }
    }

    @Override
    public void writeLimitCountViews(final @NonNull String filePath, final int limitCount) throws Exception {
        write(filePath, filter.getHabrArticlesLimitCountView(limitCount));
    }

    @Override
    public void writeUniqueCategories(final @NonNull String filePath) {
        val xstream = new XStream();
        xstream.alias("categories", Set.class);
        xstream.alias("category", String.class);

        try (val writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(filter.getUniqueCategories()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeHabrArticlesTimeToReadLessThanAverage(final @NonNull String filePath) throws Exception {
        write(filePath, filter.getHabrArticlesWhereTimeToReadLessThanAverage());
    }

    private void write(
        final @NonNull String filePath,
        final @NonNull List<HabrArticle> articles) throws IOException {

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
