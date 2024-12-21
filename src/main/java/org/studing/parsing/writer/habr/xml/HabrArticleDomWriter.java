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

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.transform.OutputKeys.INDENT;

public class HabrArticleDomWriter implements HabrArticleXmlWriter {
    private static final DateTimeFormatter FORMAT_DATE_PUBLISHED = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    final HabrArticlesFilter filter;

    public HabrArticleDomWriter(@NonNull final List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(@NonNull final String filePath) {
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
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeLimitCountViews(@NonNull final String filePath, final int limitCount) {
        try {
            writeHabrArticles(
                filter.getHabrArticlesLimitCountView(limitCount),
                filePath);
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeUniqueCategories(@NonNull final String filePath) {
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
            System.out.println(thrown.getMessage());
        }
    }

    @Override
    public void writeTimeToReadLessThanAverage(@NonNull final String filePath) {
        try {
            writeHabrArticles(
                filter.getHabrArticlesWhereTimeToReadLessThanAverage(),
                filePath);
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    private void writeOneArticle(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final HabrArticle article) {

        val articleElement = doc.createElement("article");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", article.getTitle()));
        articleElement.appendChild(createElement(doc, "author", article.getAuthor()));
        articleElement.appendChild(createElement(
            doc,
            "datePublished",
            FORMAT_DATE_PUBLISHED.format(article.getDatePublished())));
        articleElement.appendChild(createElement(doc, "timeToRead", article.getTimeToRead()));
        articleElement.appendChild(createElement(doc, "countViews", article.getCountViews()));
        articleElement.appendChild(createElement(doc, "imageUrl", article.getImageUrl()));
        articleElement.appendChild(createElement(doc, "textPreview", article.getTextPreview()));

        val categoriesElement = doc.createElement("categories");
        for (val category : article.getCategories()) {
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
