package org.studing.parsing.writer.habr.xml;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.studing.exception.XmlWriteException;
import org.studing.filter.HabrArticlesFilter;
import org.studing.parsing.writer.BaseDomXmlWriter;
import org.studing.type.HabrArticle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.studing.util.EnvParser.get;

@FieldDefaults(makeFinal = true)
public class HabrArticleDomWriter extends BaseDomXmlWriter implements HabrArticleXmlWriter {
    private static final DateTimeFormatter FORMAT_DATE_PUBLISHED;
    HabrArticlesFilter filter;

    static {
        FORMAT_DATE_PUBLISHED = ofPattern(get("HABR_ARTICLE_DATE_FORMAT"));
    }

    public HabrArticleDomWriter(@NonNull final List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) throws XmlWriteException {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val rootElement = doc.createElement("authors");
            doc.appendChild(rootElement);

            for (val entry : filter.getAuthorAndHisTitles().entrySet()) {
                val authorElement = doc.createElement("author");
                rootElement.appendChild(authorElement);

                authorElement.setAttribute("name", entry.getKey());

                val titlesElement = doc.createElement("titles");
                authorElement.appendChild(titlesElement);

                for (val title : entry.getValue()) {
                    val titleElement = doc.createElement("title");
                    titleElement.appendChild(doc.createTextNode(title));
                    titlesElement.appendChild(titleElement);
                }
            }
            transform(doc, filePath);
        } catch (TransformerException | ParserConfigurationException thrown) {
            throw new XmlWriteException(
                "Failed to write author and his Habr article titles to path: %s".formatted(filePath),
                thrown);
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws XmlWriteException {
        try {
            writeHabrArticles(
                filter.getHabrArticlesLimitCountView(limitCount),
                filePath);
        } catch (TransformerException | ParserConfigurationException thrown) {
            throw new XmlWriteException(
                "Failed to write HabrArticle limit count view to path: %s".formatted(filePath),
                thrown);
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) throws XmlWriteException {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("categories");
            doc.appendChild(root);

            for (val category : filter.getUniqueCategories().stream().toList()) {
                val categoryElement = doc.createElement("category");
                categoryElement.appendChild(doc.createTextNode(category));
                root.appendChild(categoryElement);
            }
            transform(doc, filePath);
        } catch (TransformerException | ParserConfigurationException thrown) {
            throw new XmlWriteException(
                "Failed to write HabrArticle unique categories to path: %s".formatted(filePath),
                thrown);
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) throws XmlWriteException {
        try {
            writeHabrArticles(
                filter.getHabrArticlesWhereTimeToReadLessThanAverage(),
                filePath);
        } catch (TransformerException | ParserConfigurationException thrown) {
            throw new XmlWriteException(
                "Failed to write HabrArticle with time to read less then average to path: %s".formatted(filePath),
                thrown);
        }
    }

    private void writeOneArticle(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final HabrArticle article) {

        val articleElement = doc.createElement("article");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", article.title()));
        articleElement.appendChild(createElement(doc, "author", article.author()));
        articleElement.appendChild(createElement(
            doc,
            "datePublished",
            FORMAT_DATE_PUBLISHED.format(article.datePublished())));
        articleElement.appendChild(createElement(doc, "timeToRead", article.timeToRead()));
        articleElement.appendChild(createElement(doc, "countViews", article.countViews()));
        articleElement.appendChild(createElement(doc, "imageUrl", article.imageUrl()));
        articleElement.appendChild(createElement(doc, "textPreview", article.textPreview()));

        val categoriesElement = doc.createElement("categories");
        for (val category : article.categories()) {
            categoriesElement.appendChild(createElement(doc, "category", category));
        }

        articleElement.appendChild(categoriesElement);
    }

    private Element createElement(
        @NonNull final Document document,
        @NonNull final String name,
        @NonNull final String value) {

        val element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private void writeHabrArticles(
        @NonNull final List<HabrArticle> articles,
        @NonNull final String filePath) throws ParserConfigurationException, TransformerException {

        val doc = newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("articles");
        doc.appendChild(root);

        for (val article : articles) {
            writeOneArticle(root, doc, article);
        }

        transform(doc, filePath);
    }
}