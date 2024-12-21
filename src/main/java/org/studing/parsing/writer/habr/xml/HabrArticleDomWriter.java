package org.studing.parsing.writer.habr.xml;

import lombok.NonNull;
import lombok.val;
import org.studing.filter.HabrArticlesFilter;
import org.studing.type.HabrArticle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.github.cdimascio.dotenv.Dotenv.load;
import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.transform.OutputKeys.INDENT;

public class HabrArticleDomWriter implements HabrArticleXmlWriter {
    private static final DateTimeFormatter FORMAT_DATE_PUBLISHED;
    final HabrArticlesFilter filter;

    static {
        FORMAT_DATE_PUBLISHED = ofPattern(load().get("HABR_ARTICLE_DATE_FORMAT"));
    }

    public HabrArticleDomWriter(@NonNull final List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) throws Exception {
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

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.err.println("Failed to write author and his Habr article titles to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) throws Exception {
        try {
            writeHabrArticles(
                filter.getHabrArticlesLimitCountView(limitCount),
                filePath);
        } catch (Exception thrown) {
            System.err.println("Failed to write HabrArticle limit count view to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) throws Exception {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("categories");
            doc.appendChild(root);

            for (val category : filter.getUniqueCategories().stream().toList()) {
                val categoryElement = doc.createElement("category");
                categoryElement.appendChild(doc.createTextNode(category));
                root.appendChild(categoryElement);
            }

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.err.println("Failed to write HabrArticle unique categories to: " + filePath);
            throw thrown;
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) throws Exception {
        try {
            writeHabrArticles(
                filter.getHabrArticlesWhereTimeToReadLessThanAverage(),
                filePath);
        } catch (Exception thrown) {
            System.err.println("Failed to write HabrArticle with time to read less then average to: " + filePath);
            throw thrown;
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

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }
}
