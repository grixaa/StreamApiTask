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
                HabrArticle::getAuthor,
                mapping(HabrArticle::getTitle, toList())));
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(final int limitCountView) throws Exception {
        return articles.stream()
            .filter(article -> parseInt(article.getCountViews()) > limitCountView)
            .sorted(comparing(HabrArticle::getTitle))
            .collect(toList());
    }

    public Set<String> getUniqueCategories() {
        return articles.stream()
            .flatMap(article -> article.getCategories().stream())
            .collect(toSet());
    }

    public List<HabrArticle> getHabrArticlesWhereTimeToReadLessThanAverage() throws Exception {
        val timeToRead = articles.stream()
            .map(article -> parseInt(article.getTimeToRead()))
            .toList();

        final int averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

        return articles.stream().
            filter(article -> parseInt(article.getTimeToRead()) < averageTime).
            collect(toList());
    }
}
