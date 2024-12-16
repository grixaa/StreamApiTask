package org.studing;

import lombok.NonNull;
import lombok.val;
import org.studing.parsing.flie.java.HabrParserFromJson;
import org.studing.parsing.flie.java.PerformanceFromJson;
import org.studing.parsing.result.habr.AbstractHabrArticleXmlWriter;
import org.studing.parsing.result.habr.HabrArticleDomWriter;
import org.studing.parsing.result.habr.HabrArticleXStreamWriter;
import org.studing.parsing.result.theatre.PerformanceXmlWriter;
import org.studing.type.Performance;

import java.text.ParseException;
import java.util.Date;

import static org.studing.util.path.HabrArtilcePath.*;
import static org.studing.util.path.PerformancePath.*;
import static org.studing.util.path.PerformancePath.MAP_PERFORMANCE_DATE;

public class Main {
    private final static int LIMIT_COUNT_VIEWS = 10000;
    private final static int LIMIT_AGE = 12;
    private final static Date DURATION_LIMIT;

    static {
        try {
            DURATION_LIMIT = Performance.FORMAT_DURATION.parse("1:50");
        } catch (ParseException thrown) {
            throw new RuntimeException(thrown);
        }
    }

    public static void main(String[] args) throws Exception {
        val articles = new HabrParserFromJson().parse(HABR_ARTICLES);
        startWriteHabrAllTask(new HabrArticleDomWriter(articles));
        startWriteHabrAllTask(new HabrArticleXStreamWriter(articles));

        val performanceList = new PerformanceFromJson().parse(PERFORMANCES);
        startWritePerformanceAllTask(new PerformanceXmlWriter(performanceList));
    }

    private static void startWriteHabrAllTask(final @NonNull AbstractHabrArticleXmlWriter writer) throws Exception {
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
    }

    private static void startWritePerformanceAllTask(final @NonNull PerformanceXmlWriter writer) throws Exception {
        writer.writeMapPerformanceListDate(MAP_PERFORMANCE_DATE);
        writer.writePerformanceAgeLimit(AGE_LIMIT, LIMIT_AGE);
        writer.writeUniqueTitlePerformance(UNIQUE_TITLE);
        writer.writeTask4(TASK_4, DURATION_LIMIT);
    }
}





















