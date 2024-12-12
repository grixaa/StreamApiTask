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
                        Collectors.mapping(HabrArticle::getTitle, Collectors.toList())
                ));
    }

    public List<HabrArticle> getHabrArticlesLimitCountView(int n) throws Exception {
        return articles.stream()
                .filter(article -> Integer.parseInt(article.getCountViews()) > n)
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
                .map(x -> Integer.parseInt(x.getTimeToRead()))
                .toList();

        int averageTime = timeToRead.stream().mapToInt(Integer::intValue).sum() / timeToRead.size();

        return articles.stream().
                filter(x -> Integer.parseInt(x.getTimeToRead()) < averageTime).
                collect(Collectors.toList());
    }

    public Map<String, List<String>> getAuthorAndHisPublications() {
        return articles.stream().collect(
                Collectors.groupingBy(
                        HabrArticle::getAuthor,
                        Collectors.mapping(HabrArticle::getTitle, Collectors.toList())
                ));
    }
}
