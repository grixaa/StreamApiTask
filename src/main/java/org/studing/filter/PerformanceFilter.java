package org.studing.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.Performance;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.time.DayOfWeek.*;
import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static org.studing.util.EnvParser.get;

@RequiredArgsConstructor
public class PerformanceFilter {
    private static final LocalTime TIME_EVENING;
    final List<Performance> performanceList;
    List<Performance> listPerformanceUniqueTitle;
    List<Performance> listLimitedAgePerformance;
    List<Performance> listPerformanceTask4;
    Map<Performance, List<LocalDateTime>> mapPerformanceListDateTime;

    static {
        TIME_EVENING = parse(get("TIME_EVENING"), ofPattern(get("LOCAL_TIME_FORMAT")));
    }

    public List<Performance> getLimitAgePerformance(final int ageLimit) {
        if (listLimitedAgePerformance == null) {
            val temp = performanceList.stream()
                .filter(performance -> parseInt(performance.ageLimit()) <= ageLimit)
                .sorted(comparing(Performance::duration))
                .toList();
            listLimitedAgePerformance = getListPerformanceUniqueTitle(temp);
        }
        return listLimitedAgePerformance;
    }

    public List<Performance> getListPerformanceUniqueTitle() {
        if (listPerformanceUniqueTitle == null) {
            listPerformanceUniqueTitle = getListPerformanceUniqueTitle(performanceList);
        }
        return listPerformanceUniqueTitle;
    }

    public Map<Performance, List<LocalDateTime>> getMapTitleListDate() {
        if (mapPerformanceListDateTime == null) {
            val performancesUniqueTitle = getListPerformanceUniqueTitle(performanceList);

            val uniqueTitleMap = performancesUniqueTitle.stream()
                .collect(toMap(Performance::title, performance -> performance));

            val mapPerformanceListDate = performanceList.stream()
                .collect(groupingBy(
                    performance -> uniqueTitleMap.get(performance.title()),
                    mapping(Performance::date, toList())));

            mapPerformanceListDateTime = mapPerformanceListDate.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return mapPerformanceListDateTime;
    }

    public List<Performance> getPerformanceListTask4(@NonNull final LocalTime durationLimit) {
        if (listPerformanceTask4 == null) {
            listPerformanceTask4 = performanceList.stream()
                .filter(performance -> performance.duration().isBefore(durationLimit))
                .filter(performance -> performance.date().getHour() >= TIME_EVENING.getHour())
                .filter(performance -> {
                    val dayOfWeek = performance.date().getDayOfWeek();
                    return dayOfWeek == SATURDAY || dayOfWeek == TUESDAY || dayOfWeek == THURSDAY;
                })
                .toList();
        }
        return listPerformanceTask4;
    }

    private List<Performance> getListPerformanceUniqueTitle(@NonNull final List<Performance> performanceList) {
        return performanceList.stream()
            .collect(toMap(
                Performance::title,
                performance -> performance,
                (existing, replacement) -> existing))
            .values()
            .stream()
            .toList();
    }
}
