package org.studing.filter;

import lombok.NonNull;
import lombok.val;
import org.studing.type.Performance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public PerformanceFilter(final @NonNull List<Performance> performances) {
        this.performanceList = performances;
    }

    public List<Performance> getLimitAgePerformance(final int ageLimit) {
        val temp = performanceList.stream()
            .filter(performance -> Integer.parseInt(performance.getAgeLimit()) <= ageLimit)
            .sorted(Comparator.comparing(Performance::getDuration))
            .toList();

        return getListPerformanceUniqueTitle(temp);
    }

    public List<Performance> getListPerformanceUniqueTitle() {
        return getListPerformanceUniqueTitle(performanceList);
    }

    public Map<Performance, List<Date>> getMapTitleListDate() {
        val performancesUniqueTitle = getListPerformanceUniqueTitle(performanceList);

        val uniqueTitleMap = performancesUniqueTitle.stream()
            .collect(Collectors.toMap(Performance::getTitle, performance -> performance));

        val mapPerformanceListDate = performanceList.stream()
            .filter(performance -> uniqueTitleMap.containsKey(performance.getTitle()))
            .collect(Collectors.groupingBy(
                performance -> uniqueTitleMap.get(performance.getTitle()),
                Collectors.mapping(Performance::getDate, Collectors.toList())));

        mapPerformanceListDate.values().removeIf(List::isEmpty);
        return mapPerformanceListDate;
    }

    public List<Performance> getPerformanceListTask4(final @NonNull Date durationLimit) throws Exception {
        val calendar = Calendar.getInstance();

        return performanceList.stream()
            .filter(performance -> {
                calendar.setTime(performance.getDate());
                return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
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
                val calendar_evening = Calendar.getInstance();
                calendar_evening.setTime(DATE_EVENING);
                return (calendar.get(Calendar.HOUR_OF_DAY) >= calendar_evening.get(Calendar.HOUR_OF_DAY));
            })
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(final @NonNull List<Performance> performanceList) {
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
