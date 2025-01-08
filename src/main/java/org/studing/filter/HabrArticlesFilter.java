package org.studing.filter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.HabrArticle;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.Collections.unmodifiableMap;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;

@RequiredArgsConstructor
public class HabrArticlesFilter {
    final List<HabrArticle> articles;
    List<HabrArticle> listArticlesLimitedCountView;
    List<HabrArticle> listArticlesTimeToReadLessThanAverage;
    List<String> listCategories;
    Map<String, List<String>> mapAuthorAndHisTitles;

    public Map<String, List<String>> getAuthorAndHisTitles() {
        if (isNotEmpty(mapAuthorAndHisTitles)) {
            return mapAuthorAndHisTitles;
        }

        val mutableMap = articles.stream().collect(
            groupingBy(
                HabrArticle::author,
                mapping(HabrArticle::title, toList())));
        mapAuthorAndHisTitles = unmodifiableMap(mutableMap);
        return mapAuthorAndHisTitles;
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(final int limitCountView) {
        if (isNotEmpty(listArticlesLimitedCountView)) {
            return listArticlesLimitedCountView;
        }

        listArticlesLimitedCountView = articles.stream()
            .filter(article -> parseInt(article.countViews()) > limitCountView)
            .sorted(comparing(HabrArticle::title))
            .toList();
        return listArticlesLimitedCountView;
    }

    public List<String> getUniqueCategories() {
        if (isNotEmpty(listCategories)) {
            return listCategories;
        }

        listCategories = articles.stream()
            .flatMap(article -> article.categories().stream())
            .distinct()
            .toList();
        return listCategories;
    }

    public List<HabrArticle> getHabrArticlesWhereTimeToReadLessThanAverage() {
        if (isNotEmpty(listArticlesTimeToReadLessThanAverage)) {
            return listArticlesTimeToReadLessThanAverage;
        }

        val timeToRead = articles.stream()
            .map(article -> parseInt(article.timeToRead()))
            .toList();

        val averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

        listArticlesTimeToReadLessThanAverage = articles.stream().
            filter(article -> parseInt(article.timeToRead()) < averageTime).
            toList();

        return listArticlesTimeToReadLessThanAverage;
    }
}
