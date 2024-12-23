package org.studing.starter;

import lombok.val;
import org.studing.parsing.reader.HabrArticleJsonReader;
import org.studing.parsing.reader.PerformanceJsonReader;
import org.studing.parsing.writer.habr.xml.HabrArticleDomWriter;
import org.studing.parsing.writer.habr.xml.HabrArticleXStreamWriter;
import org.studing.parsing.writer.theatre.PerformanceDomXmlWriter;

import java.time.LocalTime;

import static io.github.cdimascio.dotenv.Dotenv.load;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.studing.util.path.HabrArticlePath.*;
import static org.studing.util.path.PerformancePath.*;

public class TaskStarter {
    private static final int LIMIT_COUNT_VIEWS = 10000;
    private static final int LIMIT_AGE = 12;
    private static final LocalTime DURATION_LIMIT;

    static {
        DURATION_LIMIT = parse(load().get("DURATION_LIMIT"), ofPattern("HH:mm"));
    }

    public static void startTask11() {
        try {
            val writer = new HabrArticleDomWriter(new HabrArticleJsonReader().parse(HABR_ARTICLES));
            writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
            writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
            writer.writeTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
            writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
        } catch (Exception thrown) {
            System.err.println("Failed to solve task11:" + thrown);
        }
    }

    public static void startTask12() {
        try {
            val writer = new HabrArticleXStreamWriter(new HabrArticleJsonReader().parse(HABR_ARTICLES));
            writer.writeAuthorAndHisTitles(AUTHORS_TITLES_XSTREAM);
            writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_XSTREAM, LIMIT_COUNT_VIEWS);
            writer.writeTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_XSTREAM);
            writer.writeUniqueCategories(UNIQUE_CATEGORIES_XSTREAM);
        } catch (Exception thrown) {
            System.err.println("Failed to solve task12:" + thrown);
        }
    }

    public static void startTask2() {
        try {
            val writer = new PerformanceDomXmlWriter(new PerformanceJsonReader().parse(PERFORMANCES));
            writer.writeMapPerformanceListDate(MAP_PERFORMANCE_DATE);
            writer.writePerformanceAgeLimit(AGE_LIMIT, LIMIT_AGE);
            writer.writeUniqueTitlePerformance(UNIQUE_TITLE);
            writer.writeTask4(TASK_4, DURATION_LIMIT);
        } catch (Exception thrown) {
            System.err.println("Failed to solve task2:" + thrown);
        }
    }
}
