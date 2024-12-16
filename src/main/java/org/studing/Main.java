package org.studing;

import org.studing.parsing.flie.java.HabrParserFromJson;
import org.studing.parsing.flie.java.PerformanceFromJson;
import org.studing.parsing.result.habr.AbstractHabrArticleXmlWriter;
import org.studing.parsing.result.habr.HabrArticleDomWriter;
import org.studing.parsing.result.habr.HabrArticleXStreamWriter;
import org.studing.parsing.result.theatre.PerformanceXmlWriter;
import org.studing.type.HabrArticle;
import org.studing.type.Performance;
import org.studing.util.path.HabrArtilcePath;
import org.studing.util.path.PerformancePath;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.studing.util.path.HabrArtilcePath.*;

public class Main {
    private final static int LIMIT_COUNT_VIEWS = 10000;
    private final static int LIMIT_AGE = 12;
    private final static Date DURATION_LIMIT;

    static {
        try {
            DURATION_LIMIT = Performance.FORMAT_DURATION.parse("1:50");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {

        List<HabrArticle> articles = new HabrParserFromJson().parse(HabrArtilcePath.HABR_ARTICLES);
        startWriteHabrAllTask(new HabrArticleDomWriter(articles));
        startWriteHabrAllTask(new HabrArticleXStreamWriter(articles));

        List<Performance> performanceList = new PerformanceFromJson().parse(PerformancePath.PERFORMANCES);
        startWritePerformanceAllTask(new PerformanceXmlWriter(performanceList));
    }

    private static void startWriteHabrAllTask(AbstractHabrArticleXmlWriter writer) throws Exception {
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
    }

    private static void startWritePerformanceAllTask(PerformanceXmlWriter writer) throws Exception {
        writer.writeMapPerformanceListDate(PerformancePath.MAP_PERFORMANCE_DATE);
        writer.writePerformanceAgeLimit(PerformancePath.AGE_LIMIT, LIMIT_AGE);
        writer.writeUniqueTitlePerformance(PerformancePath.UNIQUE_TITLE);
        writer.writeTask4(PerformancePath.TASK_4, DURATION_LIMIT);
    }
}





















