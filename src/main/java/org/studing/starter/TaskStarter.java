package org.studing.starter;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.val;
import org.studing.parsing.reader.HabrArticleJsonReader;
import org.studing.parsing.reader.PerformanceJsonReader;
import org.studing.parsing.writer.habr.xml.HabrArticleDomWriter;
import org.studing.parsing.writer.habr.xml.HabrArticleXStreamWriter;
import org.studing.parsing.writer.theatre.PerformanceXmlWriter;

import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.studing.util.path.HabrArticlePath.*;
import static org.studing.util.path.PerformancePath.*;

public class TaskStarter {
    private static final int LIMIT_COUNT_VIEWS = 10000;
    private static final int LIMIT_AGE = 12;
    private static final LocalTime DURATION_LIMIT;

    static {
        var dotenv = Dotenv.load();
        DURATION_LIMIT = LocalTime.parse(dotenv.get("DURATION_LIMIT"), ofPattern("HH:mm:ss"));
    }

    public static void startTask11() {
        val writer = new HabrArticleDomWriter(new HabrArticleJsonReader().parse(HABR_ARTICLES));
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
        writer.writeTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
    }

    public static void startTask12() {
        val writer = new HabrArticleXStreamWriter(new HabrArticleJsonReader().parse(HABR_ARTICLES));
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_XSTREAM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_XSTREAM, LIMIT_COUNT_VIEWS);
        writer.writeTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_XSTREAM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_XSTREAM);
    }

    public static void startTask2() {
        val writer = new PerformanceXmlWriter(new PerformanceJsonReader().parse(PERFORMANCES));
        writer.writeMapPerformanceListDate(MAP_PERFORMANCE_DATE);
        writer.writePerformanceAgeLimit(AGE_LIMIT, LIMIT_AGE);
        writer.writeUniqueTitlePerformance(UNIQUE_TITLE);
        writer.writeTask4(TASK_4, DURATION_LIMIT);
    }
}
