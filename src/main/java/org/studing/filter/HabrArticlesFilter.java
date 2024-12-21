package org.studing.filter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.HabrArticle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class HabrArticlesFilter {
    final List<HabrArticle> articles;

    public Map<String, List<String>> getAuthorAndHisTitles() {
        return articles.stream().collect(
            groupingBy(
                HabrArticle::author,
                mapping(HabrArticle::title, toList())));
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(final int limitCountView) {
        return articles.stream()
            .filter(article -> parseInt(article.countViews()) > limitCountView)
            .sorted(comparing(HabrArticle::title))
            .collect(toList());
    }

    public Set<String> getUniqueCategories() {
        return articles.stream()
            .flatMap(article -> article.categories().stream())
            .collect(toSet());
    }

    public List<HabrArticle> getHabrArticlesWhereTimeToReadLessThanAverage() {
        val timeToRead = articles.stream()
            .map(article -> parseInt(article.timeToRead()))
            .toList();

        final int averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

        return articles.stream().
            filter(article -> parseInt(article.timeToRead()) < averageTime).
            collect(toList());
    }
}
