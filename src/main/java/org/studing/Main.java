package org.studing;

import lombok.NonNull;
import lombok.val;
import org.studing.parsing.flie.java.HabrParserFromJson;
import org.studing.parsing.flie.java.PerformanceFromJson;
import org.studing.parsing.result.habr.HabrArticleDomWriter;
import org.studing.parsing.result.habr.HabrArticleXStreamWriter;
import org.studing.parsing.result.theatre.PerformanceXmlWriter;

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
        //startTask11();
        startTask12();
        /*startTask2();*/
    }

    private static void startTask11() throws Exception {
        val writer = new HabrArticleDomWriter(new HabrParserFromJson().parse(HABR_ARTICLES));
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_DOM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_DOM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_DOM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_DOM);
    }

    private static void startTask12() throws Exception {
        val writer = new HabrArticleXStreamWriter(new HabrParserFromJson().parse(HABR_ARTICLES));
        writer.writeAuthorAndHisTitles(AUTHORS_TITLES_XSTREAM);
        writer.writeLimitCountViews(LIMIT_COUNT_VIEWS_XSTREAM, LIMIT_COUNT_VIEWS);
        writer.writeHabrArticlesTimeToReadLessThanAverage(LESS_THAN_AVERAGE_TIME_XSTREAM);
        writer.writeUniqueCategories(UNIQUE_CATEGORIES_XSTREAM);
    }

    private static void startTask2() throws Exception {
        val writer = new PerformanceXmlWriter(new PerformanceFromJson().parse(PERFORMANCES));
        writer.writeMapPerformanceListDate(MAP_PERFORMANCE_DATE);
        writer.writePerformanceAgeLimit(AGE_LIMIT, LIMIT_AGE);
        writer.writeUniqueTitlePerformance(UNIQUE_TITLE);
        writer.writeTask4(TASK_4, DURATION_LIMIT);
    }
}





















