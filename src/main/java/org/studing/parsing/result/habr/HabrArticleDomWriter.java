package org.studing.parsing.result.habr;

import org.studing.filter.HabrArticlesFilter;
import org.studing.type.HabrArticle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Map;

public class HabrArticleDomWriter extends AbstractHabrArticleXmlWriter {

    private final HabrArticlesFilter filter;

    public HabrArticleDomWriter(List<HabrArticle> habrArticles) {
        filter = new HabrArticlesFilter(habrArticles);
    }

    @Override
    public void writeAuthorAndHisTitles(String filePath) {
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeLimitCountViews(String filePath, int limitCount) throws Exception {
        writeHabrArticles(
                filter.getHabrArticlesLimitCountView(limitCount),
                filePath
        );
    }

    @Override
    public void writeUniqueCategories(String filePath) {
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeHabrArticlesTimeToReadLessThanAverage(String filePath) throws Exception {
        writeHabrArticles(
                filter.getHabrArticlesWhereTimeToReadLessThanAverage(),
                filePath
        );
    }

    private void writeOneArticle(Element root, Document doc, HabrArticle article) {
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

    private Element createElement(Document document, String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private void writeHabrArticles(List<HabrArticle> articles, String filePath) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("articles");
        doc.appendChild(root);

        for (HabrArticle article : articles) {
            writeOneArticle(root, doc, article);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
