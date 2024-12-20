package org.studing.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.Performance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.time.DayOfWeek.*;
import static java.time.ZoneId.systemDefault;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class PerformanceFilter {
    final List<Performance> performanceList;
    private static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));
    private static final LocalDate DATE_EVENING;

    static {
        try {
            DATE_EVENING = FORMAT_DURATION.parse("17:00").toInstant().atZone(systemDefault()).toLocalDate();
        } catch (ParseException thrown) {
            throw new RuntimeException(thrown);
        }
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

    public Map<Performance, List<Date>> getMapTitleListDate() {
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

    public List<Performance> getPerformanceListTask4(@NonNull final Date durationLimit) {
        return performanceList.stream()
            .filter(performance -> {
                try {
                    val performanceDuration = FORMAT_DURATION.parse(performance.getDuration());
                    val durationLocalDate = performanceDuration.toInstant().atZone(systemDefault()).toLocalDate();
                    return durationLocalDate.isAfter(durationLimit.toInstant().atZone(systemDefault()).toLocalDate());
                } catch (ParseException thrown) {
                    throw new RuntimeException(thrown);
                }
            })
            .filter(performance -> {
                val dayOfWeek = performance.getDate()
                    .toInstant()
                    .atZone(systemDefault())
                    .toLocalDate()
                    .getDayOfWeek();
                return dayOfWeek == SATURDAY || dayOfWeek == TUESDAY || dayOfWeek == THURSDAY;
            })
            .filter(performance -> {
                val performanceLocalDate = performance.getDate().toInstant().atZone(systemDefault()).toLocalDate();
                return performanceLocalDate.isEqual(DATE_EVENING) || performanceLocalDate.isAfter(DATE_EVENING);
            })
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(@NonNull final List<Performance> performanceList) {
        return performanceList.stream()
            .filter(performance -> performanceList.stream()
                .map(Performance::getTitle)
                .distinct()
                .anyMatch(title -> title.equals(performance.getTitle())))
            .distinct()
            .toList();
    }
}
