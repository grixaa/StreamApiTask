package org.studing.filter;

import org.studing.type.HabrArticle;

import java.util.*;
import java.util.stream.Collectors;

public class HabrArticlesFilter {
    private List<HabrArticle> articles;

    public HabrArticlesFilter(List<HabrArticle> habrArticles) {
        this.articles = habrArticles;
    }

    public Map<String, List<String>> getAuthorAndHisTitles() {
        return articles.stream().collect(
            Collectors.groupingBy(
                HabrArticle::getAuthor,
                Collectors.mapping(HabrArticle::getTitle, Collectors.toList())));
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(int limitCountView) throws Exception {
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
        List<Integer> timeToRead = articles.stream()
            .map(article -> Integer.parseInt(article.getTimeToRead()))
            .toList();

        int averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

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
