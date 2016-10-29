package ua.kozlov.madfood.utils;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class CalendarDateDecorator implements DayViewDecorator{

    private final int color;
    private final HashSet<CalendarDay> dates;

    public CalendarDateDecorator(int color, HashSet<CalendarDay> dates) {
        this.color = color;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(15, color));
    }
}
