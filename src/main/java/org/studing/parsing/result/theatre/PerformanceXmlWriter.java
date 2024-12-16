package org.studing.parsing.result.theatre;

import org.studing.filter.PerformanceFilter;
import org.studing.type.Performance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static javax.xml.transform.OutputKeys.INDENT;

public class PerformanceXmlWriter {
    final PerformanceFilter filter;

    public PerformanceXmlWriter(List<Performance> performanceList) {
        filter = new PerformanceFilter(performanceList);
    }

    public void writePerformanceAgeLimit(String filePath, int ageLimit) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (Performance performance : filter.getLimitAgePerformance(ageLimit)) {
            writeOnePerformance(root, doc, performance);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeUniqueTitlePerformance(String filePath) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (Performance performance : filter.getListPerformanceUniqueTitle()) {
            writeOnePerformance(root, doc, performance);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeMapPerformanceListDate(String filePath) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (Map.Entry<Performance, List<Date>> entry : filter.getMapTitleListDate().entrySet()) {
            writeOnePerformanceWithListDate(root, doc, entry.getKey(), entry.getValue());
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeTask4(String filePath, Date durationLimit) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (Performance performance : filter.getPerformanceListTask4(durationLimit)) {
            writeOnePerformance(root, doc, performance);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    private void writeOnePerformanceWithListDate(
        Element root,
        Document doc,
        Performance performance,
        List<Date> dateList) {
        Element articleElement = writeAllWithoutDate(root, doc, performance);

        Element dateElement = doc.createElement("date");
        for (Date date : dateList) {
            dateElement.appendChild(createElement(doc, "d", Performance.FORMAT_DATE.format(date)));
        }
        articleElement.appendChild(dateElement);
    }

    private void writeOnePerformance(Element root, Document doc, Performance performance) {
        Element articleElement = writeAllWithoutDate(root, doc, performance);
        articleElement.appendChild(createElement(doc, "date", Performance.FORMAT_DATE.format(performance.getDate())));
    }

    private Element writeAllWithoutDate(Element root, Document doc, Performance performance) {
        Element articleElement = doc.createElement("performance");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", performance.getTitle()));
        articleElement.appendChild(createElement(doc, "duration", performance.getDuration()));
        articleElement.appendChild(createElement(doc, "ageLimit", performance.getAgeLimit()));
        articleElement.appendChild(createElement(doc, "imageUrl", performance.getImageUrl()));

        return articleElement;
    }

    private Element createElement(Document document, String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }
}
