package org.studing.filter;

import org.studing.type.Performance;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class PerformanceFilter {
    private final List<Performance> performanceList;
    private static final Date DATE_EVENING;

    static {
        try {
            DATE_EVENING = Performance.FORMAT_DURATION.parse("17:00");
        } catch (ParseException thrown) {
            throw new RuntimeException(thrown);
        }
    }

    public PerformanceFilter(List<Performance> performances) {
        this.performanceList = performances;
    }

    public List<Performance> getLimitAgePerformance(int ageLimit) {
        List<Performance> temp = performanceList.stream()
            .filter(performance -> Integer.parseInt(performance.getAgeLimit()) <= ageLimit)
            .sorted(Comparator.comparing(Performance::getDuration))
            .toList();

        return getListPerformanceUniqueTitle(temp);
    }

    public List<Performance> getListPerformanceUniqueTitle() {
        return getListPerformanceUniqueTitle(performanceList);
    }

    public Map<Performance, List<Date>> getMapTitleListDate() {
        List<Performance> performancesUniqueTitle = getListPerformanceUniqueTitle(performanceList);

        Map<String, Performance> uniqueTitleMap = performancesUniqueTitle.stream()
            .collect(Collectors.toMap(Performance::getTitle, performance -> performance));

        Map<Performance, List<Date>> mapPerformanceListDate = performanceList.stream()
            .filter(performance -> uniqueTitleMap.containsKey(performance.getTitle()))
            .collect(Collectors.groupingBy(
                performance -> uniqueTitleMap.get(performance.getTitle()),
                Collectors.mapping(Performance::getDate, Collectors.toList())));

        mapPerformanceListDate.values().removeIf(List::isEmpty);
        return mapPerformanceListDate;
    }

    public List<Performance> getPerformanceListTask4(Date durationLimit) throws Exception {
        Calendar calendar = Calendar.getInstance();

        return performanceList.stream()
            .filter(performance -> {
                calendar.setTime(performance.getDate());
                return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
            })
            .filter(performance -> {
                try {
                    return Performance.FORMAT_DURATION.parse(performance.getDuration()).after(durationLimit);
                } catch (ParseException thrown) {
                    throw new RuntimeException(thrown);
                }
            })
            .filter(performance -> {
                calendar.setTime(performance.getDate());
                Calendar calendar_evening = Calendar.getInstance();
                calendar_evening.setTime(DATE_EVENING);
                return (calendar.get(Calendar.HOUR_OF_DAY) >= calendar_evening.get(Calendar.HOUR_OF_DAY));
            })
            .toList();
    }

    private List<Performance> getListPerformanceUniqueTitle(List<Performance> performanceList) {
        List<Performance> temp = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        performanceList.forEach(performance -> {
            if (!titles.contains(performance.getTitle())) {
                titles.add(performance.getTitle());
                temp.add(performance);
            }
        });

        return temp;
    }
}
