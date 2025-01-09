package org.studing;

import lombok.val;
import org.slf4j.Logger;
import org.studing.exception.XmlWriteException;
import org.studing.parsing.reader.HabrArticleJsonReader;
import org.studing.parsing.reader.PerformanceJsonReader;
import org.studing.parsing.writer.habr.xml.HabrArticleDomWriter;
import org.studing.parsing.writer.habr.xml.HabrArticleXStreamWriter;
import org.studing.parsing.writer.theatre.PerformanceDomXmlWriter;

import java.io.IOException;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.slf4j.LoggerFactory.getLogger;
import static org.studing.util.EnvParser.get;

public class Main {
    private static final int LIMIT_COUNT_VIEWS;
    private static final int LIMIT_AGE;
    private static final LocalTime DURATION_LIMIT;
    private static final Logger logger = getLogger(Main.class);

    static {
        DURATION_LIMIT = parse(get("DURATION_LIMIT"), ofPattern("HH:mm"));
        LIMIT_COUNT_VIEWS = parseInt(get("LIMIT_COUNT_VIEWS"));
        LIMIT_AGE = parseInt(get("LIMIT_AGE"));
    }

    public static void main(String[] args) {
        startTask11();
        startTask12();
        startTask2();
    }

    public static void startTask11() {
        try {
            val writer = new HabrArticleDomWriter(new HabrArticleJsonReader().parse(get("HABR_ARTICLES")));
            writer.writeAuthorAndHisTitles(get("AUTHORS_TITLES_DOM"));
            writer.writeLimitCountViews(get("LIMIT_COUNT_VIEWS_DOM"), LIMIT_COUNT_VIEWS);
            writer.writeTimeToReadLessThanAverage(get("LESS_THAN_AVERAGE_TIME_DOM"));
            writer.writeUniqueCategories(get("UNIQUE_CATEGORIES_DOM"));
        } catch (XmlWriteException | IOException thrown) {
            logger.error("Failed to solve task11", thrown);
        }
    }

    public static void startTask12() {
        try {
            val writer = new HabrArticleXStreamWriter(new HabrArticleJsonReader().parse(get("HABR_ARTICLES")));
            writer.writeAuthorAndHisTitles(get("AUTHORS_TITLES_XSTREAM"));
            writer.writeLimitCountViews(get("LIMIT_COUNT_VIEWS_XSTREAM"), LIMIT_COUNT_VIEWS);
            writer.writeTimeToReadLessThanAverage(get("LESS_THAN_AVERAGE_TIME_XSTREAM"));
            writer.writeUniqueCategories(get("UNIQUE_CATEGORIES_XSTREAM"));
        } catch (XmlWriteException | IOException thrown) {
            logger.error("Failed to solve task12", thrown);
        }
    }

    public static void startTask2() {
        try {
            val writer = new PerformanceDomXmlWriter(new PerformanceJsonReader().parse(get("PERFORMANCES")));
            writer.writeMapPerformanceListDate(get("MAP_PERFORMANCE_DATE"));
            writer.writePerformanceAgeLimit(get("AGE_LIMIT"), LIMIT_AGE);
            writer.writeUniqueTitlePerformance(get("UNIQUE_TITLE"));
            writer.writeTask4(get("TASK_4"), DURATION_LIMIT);
        } catch (XmlWriteException | IOException thrown) {
            logger.error("Failed to solve task2:", thrown);
        }
    }
}