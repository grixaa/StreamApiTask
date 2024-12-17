package org.studing.parsing.result.habr;

import lombok.NonNull;
import lombok.val;
import org.studing.filter.HabrArticlesFilter;
import org.studing.type.HabrArticle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static javax.xml.transform.OutputKeys.INDENT;

public class HabrArticleDomWriter extends AbstractHabrArticleXmlWriter {
    private static final DateFormat FORMAT_DATE_PUBLISHED = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
    final HabrArticlesFilter filter;

    public HabrArticleDomWriter(final @NonNull List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(final @NonNull String filePath) {
        try {
            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
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
            throw new RuntimeException(thrown);
        }
    }

    @Override
    public void writeLimitCountViews(final @NonNull String filePath, final int limitCount) throws Exception {
        writeHabrArticles(
            filter.getHabrArticlesLimitCountView(limitCount),
            filePath);
    }

    @Override
    public void writeUniqueCategories(final @NonNull String filePath) {
        try {
            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
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
            throw new RuntimeException(thrown);
        }
    }

    @Override
    public void writeHabrArticlesTimeToReadLessThanAverage(final @NonNull String filePath) throws Exception {
        writeHabrArticles(
            filter.getHabrArticlesWhereTimeToReadLessThanAverage(),
            filePath);
    }

    private void writeOneArticle(final @NonNull Element root,
                                 final @NonNull Document doc,
                                 final @NonNull HabrArticle article) {

        val articleElement = doc.createElement("article");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", article.getTitle()));
        articleElement.appendChild(createElement(doc, "author", article.getAuthor()));
        articleElement.appendChild(createElement(doc, "datePublished", FORMAT_DATE_PUBLISHED.format(article.getDatePublished())));
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

    private Element createElement(final @NonNull Document document,
                                  final @NonNull String name,
                                  final @NonNull String value) {

        val element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private void writeHabrArticles(final @NonNull List<HabrArticle> articles,
                                   final @NonNull String filePath) throws Exception {

        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("articles");
        doc.appendChild(root);

        for (val article : articles) {
            writeOneArticle(root, doc, article);
        }

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    /***
     * CHANGE METHOD
     */
    public void method(String filePath) throws Exception {
        try {
            Map<String, List<String>> authorsMap = filter.getAuthorAndHisTitles();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = doc.createElement("authors");
            doc.appendChild(rootElement);

            for (Map.Entry<String, List<String>> entry : authorsMap.entrySet()) {
                String author = entry.getKey();
                List<String> titles = entry.getValue();

                Element authorElement = doc.createElement("author");
                rootElement.appendChild(authorElement);

                authorElement.setAttribute("name", author);

                Element titlesElement = doc.createElement("titles");
                authorElement.appendChild(titlesElement);

                for (String title : titles) {
                    Element titleElement = doc.createElement("title");
                    titleElement.appendChild(doc.createTextNode(title));
                    titlesElement.appendChild(titleElement);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            throw new RuntimeException(thrown);
        }
    }
}
