package org.studing.parsing.result.habr;

import com.thoughtworks.xstream.XStream;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.wrapper.AuthorWrapper;
import org.studing.type.HabrArticle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HabrArticleXStreamWriter extends AbstractHabrArticleXmlWriter {

    private final HabrArticlesFilter filter;

    public HabrArticleXStreamWriter(List<HabrArticle> articles) {
        this.filter = new HabrArticlesFilter(articles);
    }

    @Override
    public void writeAuthorAndHisTitles(String filePath) {
        Map<String, List<String>> authorAndHisTitles = filter.getAuthorAndHisTitles();

        XStream xstream = new XStream();
        xstream.alias("authors", List.class);
        xstream.alias("author", AuthorWrapper.class);

        xstream.alias("title", String.class);
        xstream.useAttributeFor(AuthorWrapper.class, "titles");

        List<AuthorWrapper> authorsList = new ArrayList<>();
        authorAndHisTitles.forEach((k, v) -> authorsList.add(new AuthorWrapper(k, v)));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(authorsList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeLimitCountViews(String filePath, int limitCount) throws Exception {
        List<HabrArticle> articles = filter.getHabrArticlesLimitCountView(limitCount);
        write(filePath, articles);
    }

    @Override
    public void writeUniqueCategories(String filePath) {
        Set<String> uniqueCategories = filter.getUniqueCategories();

        XStream xstream = new XStream();
        xstream.alias("categories", Set.class);
        xstream.alias("category", String.class);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(uniqueCategories));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeHabrArticlesTimeToReadLessThanAverage(String filePath) throws Exception {
        List<HabrArticle> articles = filter.getHabrArticlesWhereTimeToReadLessThanAverage();
        write(filePath, articles);
    }

    private void write(String filePath, List<HabrArticle> articles) throws IOException {
        XStream xstream = new XStream();
        xstream.alias("articles", List.class);
        xstream.alias("article", HabrArticle.class);
        xstream.alias("category", String.class);
        xstream.useAttributeFor(HabrArticle.class, "categories");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xstream.toXML(articles));
        }
    }
}
