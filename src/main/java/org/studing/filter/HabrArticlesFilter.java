package org.studing.filter;

import lombok.NonNull;
import lombok.val;
import org.studing.type.HabrArticle;

import java.util.*;
import java.util.stream.Collectors;

public class HabrArticlesFilter {
    final List<HabrArticle> articles;

    public HabrArticlesFilter(final @NonNull List<HabrArticle> habrArticles) {
        this.articles = habrArticles;
    }

    public Map<String, List<String>> getAuthorAndHisTitles() {
        return articles.stream().collect(
            Collectors.groupingBy(
                HabrArticle::getAuthor,
                Collectors.mapping(HabrArticle::getTitle, Collectors.toList())));
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(final int limitCountView) throws Exception {
        return articles.stream()
            .filter(article -> Integer.parseInt(article.getCountViews()) > limitCountView)
            .sorted(Comparator.comparing(HabrArticle::getTitle))
            .collect(Collectors.toList());
    }

    public Set<String> getUniqueCategories() {
        return articles.stream()
            .flatMap(article -> article.getCategories().stream())
            .collect(Collectors.toSet());
    }

    public List<HabrArticle> getHabrArticlesWhereTimeToReadLessThanAverage() throws Exception {
        val timeToRead = articles.stream()
            .map(article -> Integer.parseInt(article.getTimeToRead()))
            .toList();

        final int averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

        return articles.stream().
            filter(article -> Integer.parseInt(article.getTimeToRead()) < averageTime).
            collect(Collectors.toList());
    }

    public Map<String, List<String>> getAuthorAndHisPublications() {
        return articles.stream().collect(
            Collectors.groupingBy(
                HabrArticle::getAuthor,
                Collectors.mapping(HabrArticle::getTitle, Collectors.toList())));
    }
}
