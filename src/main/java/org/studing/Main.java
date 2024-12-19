package org.studing;

import lombok.NonNull;
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

import static org.studing.util.path.HabrArtilcePath.*;
import static org.studing.util.path.PerformancePath.*;

public class Main {
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

    public static void main(String[] args) throws Exception {
        val articles = new HabrArticleJsonReader().parse(HABR_ARTICLES);
        startWriteHabrAllTask(new HabrArticleDomWriter(articles));
        startWriteHabrAllTask(new HabrArticleXStreamWriter(articles));

        val performanceList = new PerformanceJsonReader().parse(PERFORMANCES);
        startWritePerformanceAllTask(new PerformanceXmlWriter(performanceList));
    }

    private static void startWriteHabrAllTask(final @NonNull HabrArticleDomWriter writer) throws Exception {
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
    }

    private static void startWriteHabrAllTask(final @NonNull HabrArticleXStreamWriter writer) throws Exception {
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_XSTREAM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_XSTREAM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_XSTREAM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_XSTREAM);
    }

    private static void startWritePerformanceAllTask(final @NonNull PerformanceXmlWriter writer) throws Exception {
        writer.writeMapPerformanceListDate(MAP_PERFORMANCE_DATE);
        writer.writePerformanceAgeLimit(AGE_LIMIT, LIMIT_AGE);
        writer.writeUniqueTitlePerformance(UNIQUE_TITLE);
        writer.writeTask4(TASK_4, DURATION_LIMIT);
    }
}





















