package org.studing.parsing.writer.theatre;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.studing.filter.PerformanceFilter;
import org.studing.parsing.writer.BaseDomXmlWriter;
import org.studing.type.Performance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.studing.util.EnvParser.get;

@FieldDefaults(makeFinal = true)
public class PerformanceDomXmlWriter extends BaseDomXmlWriter {
    private static final DateTimeFormatter FORMAT_DATE;
    PerformanceFilter filter;

    static {
        FORMAT_DATE = ofPattern(get("PERFORMANCE_DATE_FORMAT"), new Locale("ru"));
    }

    public PerformanceDomXmlWriter(@NonNull final List<Performance> performanceList) {
        filter = new PerformanceFilter(performanceList);
    }

    public void writePerformanceAgeLimit(
        @NonNull final String filePath,
        final int ageLimit) throws Exception {

        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getLimitAgePerformance(ageLimit)) {
                writeOnePerformance(root, doc, performance);
            }
            transform(doc, filePath);

        } catch (Exception thrown) {
            System.err.println("Failed to write list performance age-limit to " + filePath);
            throw thrown;
        }
    }

    public void writeUniqueTitlePerformance(@NonNull final String filePath) throws Exception {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getListPerformanceUniqueTitle()) {
                writeOnePerformance(root, doc, performance);
            }
            transform(doc, filePath);

        } catch (Exception thrown) {
            System.err.println("Failed to write unique-title performance to " + filePath);
            throw thrown;
        }
    }

    public void writeMapPerformanceListDate(@NonNull final String filePath) throws Exception {
        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val entry : filter.getMapTitleListDate().entrySet()) {
                writeOnePerformanceWithListDate(root, doc, entry.getKey(), entry.getValue());
            }
            transform(doc, filePath);
        } catch (Exception thrown) {
            System.err.println("Failed to write map (performance, list date) to " + filePath);
            throw thrown;
        }
    }

    public void writeTask4(
        @NonNull final String filePath,
        @NonNull final LocalTime durationLimit) throws Exception {

        try {
            val doc = newInstance().newDocumentBuilder().newDocument();
            val root = doc.createElement("performanceList");
            doc.appendChild(root);

            for (val performance : filter.getPerformanceListTask4(durationLimit)) {
                writeOnePerformance(root, doc, performance);
            }
            transform(doc, filePath);
        } catch (Exception thrown) {
            System.err.println("Failed to write task4 to " + filePath);
            throw thrown;
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
        articleElement.appendChild(createElement(doc, "date", FORMAT_DATE.format(performance.date())));
    }

    private Element writeAllWithoutDate(
        @NonNull final Element root,
        @NonNull final Document doc,
        @NonNull final Performance performance) {

        val articleElement = doc.createElement("performance");
        root.appendChild(articleElement);

        articleElement.appendChild(createElement(doc, "title", performance.title()));
        articleElement.appendChild(createElement(doc, "duration", performance.duration().toString()));
        articleElement.appendChild(createElement(doc, "ageLimit", performance.ageLimit()));
        articleElement.appendChild(createElement(doc, "imageUrl", performance.imageUrl()));

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
