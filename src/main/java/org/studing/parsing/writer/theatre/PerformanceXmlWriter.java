package org.studing.parsing.writer.theatre;

import lombok.NonNull;
import lombok.val;
import org.studing.filter.PerformanceFilter;
import org.studing.type.Performance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.transform.OutputKeys.INDENT;

public class PerformanceXmlWriter {
    private static final DateTimeFormatter FORMAT_DATE = ofPattern("dd MMMM yyyy' в 'HH:mm", new Locale("ru"));


    final PerformanceFilter filter;

    public PerformanceXmlWriter(@NonNull final List<Performance> performanceList) {
        filter = new PerformanceFilter(performanceList);
    }

    public void writePerformanceAgeLimit(
        @NonNull final String filePath,
        final int ageLimit) {

        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getLimitAgePerformance(ageLimit)) {
                writeOnePerformance(root, doc, performance);
            }

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    public void writeUniqueTitlePerformance(@NonNull final String filePath) {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getListPerformanceUniqueTitle()) {
                writeOnePerformance(root, doc, performance);
            }

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    public void writeMapPerformanceListDate(@NonNull final String filePath) {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val entry : filter.getMapTitleListDate().entrySet()) {
                writeOnePerformanceWithListDate(root, doc, entry.getKey(), entry.getValue());
            }

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    public void writeTask4(
        @NonNull final String filePath,
        @NonNull final LocalTime durationLimit) {

        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getPerformanceListTask4(durationLimit)) {
                writeOnePerformance(root, doc, performance);
            }

            val transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
        } catch (Exception thrown) {
            System.out.println(thrown.getMessage());
        }
    }

    private void writeOnePerformanceWithListDate(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final Performance performance,
        @NonNull final List<LocalDateTime> dateList) {

        val articleElement = writeAllWithoutDate(root, doc, performance);
        val dateElement = doc.createElement("date");

        for (val date : dateList) {
            dateElement.appendChild(createElement(doc, "d", FORMAT_DATE.format(date)));
        }
        articleElement.appendChild(dateElement);
    }

    private void writeOnePerformance(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final Performance performance) {

        val articleElement = writeAllWithoutDate(root, doc, performance);
        articleElement.appendChild(createElement(doc, "date", FORMAT_DATE.format(performance.getDate())));
    }

    private Element writeAllWithoutDate(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final Performance performance) {

        val articleElement = doc.createElement("performance");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", performance.getTitle()));
        articleElement.appendChild(createElement(doc, "duration", performance.getDuration().toString()));
        articleElement.appendChild(createElement(doc, "ageLimit", performance.getAgeLimit()));
        articleElement.appendChild(createElement(doc, "imageUrl", performance.getImageUrl()));

        return articleElement;
    }

    private Element createElement(
        @NonNull final Document document,
        @NonNull final String name,
        @NonNull final String value) {

        val element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }
}
