package org.studing.filter;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.Performance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.time.DayOfWeek.*;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class PerformanceFilter {
    final List<Performance> performanceList;
    private static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));
    private static final LocalTime DATE_EVENING;

    static {
        var dotenv = Dotenv.load();
        DATE_EVENING = LocalTime.parse(dotenv.get("DATE_EVENING"), ofPattern("HH:mm:ss"));
        System.out.println(DATE_EVENING);
    }

    public List<Performance> getLimitAgePerformance(final int ageLimit) {
        val temp = performanceList.stream()
            .filter(performance -> parseInt(performance.getAgeLimit()) <= ageLimit)
            .sorted(comparing(Performance::getDuration))
            .toList();

        return getListPerformanceUniqueTitle(temp);
    }

    public List<Performance> getListPerformanceUniqueTitle() {
        return getListPerformanceUniqueTitle(performanceList);
    }

    public Map<Performance, List<LocalDateTime>> getMapTitleListDate() {
        val performancesUniqueTitle = getListPerformanceUniqueTitle(performanceList);

        val uniqueTitleMap = performancesUniqueTitle.stream()
            .collect(toMap(Performance::getTitle, performance -> performance));

        val mapPerformanceListDate = performanceList.stream()
            .filter(performance -> uniqueTitleMap.containsKey(performance.getTitle()))
            .collect(groupingBy(
                performance -> uniqueTitleMap.get(performance.getTitle()),
                mapping(Performance::getDate, toList())));

        mapPerformanceListDate.values().removeIf(List::isEmpty);
        return mapPerformanceListDate;
    }

    public List<Performance> getPerformanceListTask4(@NonNull final LocalTime durationLimit) throws Exception {
        return performanceList.stream()
            .filter(performance -> performance.getDuration().isBefore(durationLimit))
            .filter(performance -> {
                val dayOfWeek = performance.getDate().getDayOfWeek();
                return dayOfWeek == SATURDAY || dayOfWeek == TUESDAY || dayOfWeek == THURSDAY;
            })
            .filter(performance -> performance.getDate().getHour() >= DATE_EVENING.getHour())
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(@NonNull final List<Performance> performanceList) {
        return new ArrayList<>(performanceList.stream()
            .collect(toMap(
                Performance::getTitle,
                performance -> performance,
                (existing, replacement) -> existing))
            .values());
    }
}
