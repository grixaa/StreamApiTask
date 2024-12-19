package org.studing.parsing.result.habr;

import com.thoughtworks.xstream.XStream;
import lombok.NonNull;
import lombok.val;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.wrapper.ArticleWrapper;
import org.studing.parsing.wrapper.AuthorWrapper;
import org.studing.parsing.wrapper.AuthorsWrapper;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class HabrArticleXStreamWriter extends AbstractHabrArticleXmlWriter {
    final HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(final @NonNull List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(final @NonNull String filePath) {
        val authorsList = filter.getAuthorAndHisTitles()
            .entrySet()
            .stream()
            .map(entry -> new AuthorWrapper(
                entry.getKey(),
                entry.getValue().stream()
                    .map(pair -> new ArticleWrapper(pair.getLeft(), pair.getRight()))
                    .toList()
            ))
            .toList();

        val xstream = new XStream();
        xstream.processAnnotations(AuthorsWrapper.class);
        xstream.processAnnotations(AuthorWrapper.class);
        xstream.processAnnotations(ArticleWrapper.class);

        try (val writer = new FileWriter(filePath)) {
            writer.write(xstream.toXML(new AuthorsWrapper(authorsList)));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
