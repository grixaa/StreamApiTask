package org.studing.parsing.result.theatre;

import lombok.NonNull;
import lombok.val;
import org.studing.filter.PerformanceFilter;
import org.studing.type.Performance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static javax.xml.transform.OutputKeys.INDENT;

public class PerformanceXmlWriter {
    private static final DateFormat FORMAT_DATE = new SimpleDateFormat("dd MMMM yyyy' в 'HH:mm", new Locale("ru"));
    final PerformanceFilter filter;

    public PerformanceXmlWriter(final @NonNull List<Performance> performanceList) {
        filter = new PerformanceFilter(performanceList);
    }

    public void writePerformanceAgeLimit(
        final @NonNull String filePath,
        final int ageLimit) throws Exception {

        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (val performance : filter.getLimitAgePerformance(ageLimit)) {
            writeOnePerformance(root, doc, performance);
        }

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeUniqueTitlePerformance(final @NonNull String filePath) throws Exception {
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (val performance : filter.getListPerformanceUniqueTitle()) {
            writeOnePerformance(root, doc, performance);
        }

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeMapPerformanceListDate(final @NonNull String filePath) throws Exception {
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (val entry : filter.getMapTitleListDate().entrySet()) {
            writeOnePerformanceWithListDate(root, doc, entry.getKey(), entry.getValue());
        }

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    public void writeTask4(
        final @NonNull String filePath,
        final @NonNull Date durationLimit) throws Exception {

        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        val root = doc.createElement("performanceList");
        doc.appendChild(root);

        for (val performance : filter.getPerformanceListTask4(durationLimit)) {
            writeOnePerformance(root, doc, performance);
        }

        val transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    private void writeOnePerformanceWithListDate(
        final @NonNull Element root,
        final @NonNull Document doc,
        final @NonNull Performance performance,
        final @NonNull List<Date> dateList) {

        val articleElement = writeAllWithoutDate(root, doc, performance);
        val dateElement = doc.createElement("date");

        for (val date : dateList) {
            dateElement.appendChild(createElement(doc, "d", FORMAT_DATE.format(date)));
        }
        articleElement.appendChild(dateElement);
    }

    private void writeOnePerformance(
        final @NonNull Element root,
        final @NonNull Document doc,
        final @NonNull Performance performance) {

        val articleElement = writeAllWithoutDate(root, doc, performance);
        articleElement.appendChild(createElement(doc, "date", FORMAT_DATE.format(performance.getDate())));
    }

    private Element writeAllWithoutDate(
        final @NonNull Element root,
        final @NonNull Document doc,
        final @NonNull Performance performance) {

        val articleElement = doc.createElement("performance");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", performance.getTitle()));
        articleElement.appendChild(createElement(doc, "duration", performance.getDuration()));
        articleElement.appendChild(createElement(doc, "ageLimit", performance.getAgeLimit()));
        articleElement.appendChild(createElement(doc, "imageUrl", performance.getImageUrl()));

        return articleElement;
    }

    private Element createElement(
        final @NonNull Document document,
        final @NonNull String name,
        final @NonNull String value) {

        val element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }
}
