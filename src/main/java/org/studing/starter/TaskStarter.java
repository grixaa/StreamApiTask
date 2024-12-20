package org.studing.starter;

import lombok.val;
import org.studing.parsing.reader.HabrArticleJsonReader;
import org.studing.parsing.reader.PerformanceJsonReader;
import org.studing.parsing.writer.habr.xml.HabrArticleDomWriter;
import org.studing.parsing.writer.habr.xml.HabrArticleXStreamWriter;
import org.studing.parsing.writer.theatre.PerformanceXmlWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.studing.util.path.HabrArticlePath.*;
import static org.studing.util.path.PerformancePath.*;

public class TaskStarter {
    private static final int LIMIT_COUNT_VIEWS = 10000;
    private static final int LIMIT_AGE = 12;
    private static final Date DURATION_LIMIT;
    private static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));

    static {
        try {
            DURATION_LIMIT = FORMAT_DURATION.parse("1:50");
        } catch (ParseException thrown) {
            throw new RuntimeException(thrown);
        }
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
