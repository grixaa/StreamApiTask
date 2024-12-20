package org.studing.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.studing.type.Performance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.util.Calendar.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class PerformanceFilter {
    final List<Performance> performanceList;
    private static final DateFormat FORMAT_DURATION = new SimpleDateFormat("H:mm", new Locale("ru"));
    private static final Date DATE_EVENING;

    static {
        try {
            DATE_EVENING = FORMAT_DURATION.parse("17:00");
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
        val calendar = getInstance();

        return performanceList.stream()
            .filter(performance -> {
                calendar.setTime(performance.getDate());
                return calendar.get(DAY_OF_WEEK) == SATURDAY ||
                    calendar.get(DAY_OF_WEEK) == TUESDAY ||
                    calendar.get(DAY_OF_WEEK) == THURSDAY;
            })
            .filter(performance -> {
                try {
                    return FORMAT_DURATION.parse(performance.getDuration()).after(durationLimit);
                } catch (ParseException thrown) {
                    throw new RuntimeException(thrown);
                }
            })
            .filter(performance -> {
                calendar.setTime(performance.getDate());
                val calendarEvening = getInstance();
                calendarEvening.setTime(DATE_EVENING);
                return (calendar.get(HOUR_OF_DAY) >= calendarEvening.get(HOUR_OF_DAY));
            })
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(@NonNull final List<Performance> performanceList) {
        val temp = new ArrayList<Performance>();
        val titles = new ArrayList<>();

        performanceList.forEach(performance -> {
            if (!titles.contains(performance.getTitle())) {
                titles.add(performance.getTitle());
                temp.add(performance);
            }
        });

        return temp;
    }
}
