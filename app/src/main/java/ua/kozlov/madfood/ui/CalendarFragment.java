package ua.kozlov.madfood.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.PlanDataR;
import ua.kozlov.madfood.utils.CalendarDateDecorator;
import ua.kozlov.madfood.utils.DebugLogger;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView mCalendarView;
    private final String mSharedDate = "date";
    private Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        mRealm = Realm.getDefaultInstance();
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        RealmResults<PlanDataR> results = DatabaseManager.getAllPlanData(mRealm);
        HashSet<CalendarDay> dates = new HashSet<>();
        for (int i = 0; i < results.size(); i++) {
            Date date = new Date();
            date.setTime(results.get(i).getDate());
            dates.add(new CalendarDay(date));
        }
        mCalendarView.addDecorator(new CalendarDateDecorator(Color.GREEN, dates));
        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            DebugLogger.log(date.getDate().getTime() + "");
            SharedPreferences sPref =
                    view.getContext().getSharedPreferences(mSharedDate, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putLong(mSharedDate, date.getDate().getTime());
            editor.apply();
            CalendarPlanningFragment planningFragment = new CalendarPlanningFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.mainContainer, planningFragment);
            transaction.commit();
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRealm.close();
    }
}