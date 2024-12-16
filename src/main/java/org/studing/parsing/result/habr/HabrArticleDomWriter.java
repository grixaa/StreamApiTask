package org.studing.parsing.result.habr;

import lombok.NonNull;
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
import java.util.List;
import java.util.Map;

import static javax.xml.transform.OutputKeys.INDENT;

public class HabrArticleDomWriter extends AbstractHabrArticleXmlWriter {
    final HabrArticlesFilter filter;

    public HabrArticleDomWriter(final @NonNull List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(final @NonNull String filePath) {
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

    @Override
    public void writeLimitCountViews(final @NonNull String filePath, final int limitCount) throws Exception {
        writeHabrArticles(
            filter.getHabrArticlesLimitCountView(limitCount),
            filePath);
    }

    @Override
    public void writeUniqueCategories(final @NonNull String filePath) {
        try {
            List<String> categories = filter.getUniqueCategories().stream().toList();

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("categories");
            doc.appendChild(root);

            for (String category : categories) {
                Element categoryElement = doc.createElement("category");
                categoryElement.appendChild(doc.createTextNode(category));
                root.appendChild(categoryElement);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
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

    private void writeOneArticle(@NonNull Element root,
                                 @NonNull Document doc,
                                 final @NonNull HabrArticle article) {

        Element articleElement = doc.createElement("article");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", article.getTitle()));
        articleElement.appendChild(createElement(doc, "author", article.getAuthor()));
        articleElement.appendChild(createElement(doc, "datePublished", article.getDatePublished().toString()));
        articleElement.appendChild(createElement(doc, "timeToRead", article.getTimeToRead()));
        articleElement.appendChild(createElement(doc, "countViews", article.getCountViews()));
        articleElement.appendChild(createElement(doc, "imageUrl", article.getImageUrl()));
        articleElement.appendChild(createElement(doc, "textPreview", article.getTextPreview()));

        Element categoriesElement = doc.createElement("categories");
        for (String category : article.getCategories()) {
            categoriesElement.appendChild(createElement(doc, "category", category));
        }
        articleElement.appendChild(categoriesElement);
    }

    private Element createElement(@NonNull Document document,
                                  final @NonNull String name,
                                  final @NonNull String value) {

        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private void writeHabrArticles(final @NonNull List<HabrArticle> articles,
                                   final @NonNull String filePath) throws Exception {

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("articles");
        doc.appendChild(root);

        for (HabrArticle article : articles) {
            writeOneArticle(root, doc, article);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
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
