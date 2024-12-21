package org.studing.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.studing.type.Performance;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.cdimascio.dotenv.Dotenv.load;
import static java.lang.Integer.parseInt;
import static java.time.DayOfWeek.*;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class PerformanceFilter {
    List<Performance> performanceList;
    private static final LocalTime TIME_EVENING;

    static {
        val dotenv = load();
        TIME_EVENING = parse(dotenv.get("TIME_EVENING"), ofPattern(dotenv.get("LOCAL_TIME_FORMAT")));
    }

    public List<Performance> getLimitAgePerformance(final int ageLimit) {
        val temp = performanceList.stream()
            .filter(performance -> parseInt(performance.ageLimit()) <= ageLimit)
            .sorted(comparing(Performance::duration))
            .toList();

        return getListPerformanceUniqueTitle(temp);
    }

    public List<Performance> getListPerformanceUniqueTitle() {
        return getListPerformanceUniqueTitle(performanceList);
    }

    public Map<Performance, List<LocalDateTime>> getMapTitleListDate() {
        val performancesUniqueTitle = getListPerformanceUniqueTitle(performanceList);

        val uniqueTitleMap = performancesUniqueTitle.stream()
            .collect(toMap(Performance::title, performance -> performance));

        val mapPerformanceListDate = performanceList.stream()
            .filter(performance -> uniqueTitleMap.containsKey(performance.title()))
            .collect(groupingBy(
                performance -> uniqueTitleMap.get(performance.title()),
                mapping(Performance::date, toList())));

        mapPerformanceListDate.values().removeIf(List::isEmpty);
        return mapPerformanceListDate;
    }

    public List<Performance> getPerformanceListTask4(@NonNull final LocalTime durationLimit) {
        return performanceList.stream()
            .filter(performance -> performance.duration().isBefore(durationLimit))
            .filter(performance -> performance.date().getHour() >= TIME_EVENING.getHour())
            .filter(performance -> {
                val dayOfWeek = performance.date().getDayOfWeek();
                return dayOfWeek == SATURDAY || dayOfWeek == TUESDAY || dayOfWeek == THURSDAY;
            })
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(@NonNull final List<Performance> performanceList) {
        return new ArrayList<>(performanceList.stream()
            .collect(toMap(
                Performance::title,
                performance -> performance,
                (existing, replacement) -> existing))
            .values());
    }
}
